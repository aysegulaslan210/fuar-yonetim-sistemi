package com.fuar.yonetim.service;

import com.fuar.yonetim.entity.BoothArea;
import com.fuar.yonetim.entity.InfrastructureResource;
import com.fuar.yonetim.entity.Reservation;
import com.fuar.yonetim.entity.ReservationStatus;
import com.fuar.yonetim.exception.BusinessRuleException;
import com.fuar.yonetim.exception.ResourceNotFoundException;
import com.fuar.yonetim.repository.BoothAreaRepository;
import com.fuar.yonetim.repository.InfrastructureResourceRepository;
import com.fuar.yonetim.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final BoothAreaRepository boothAreaRepository;
    private final InfrastructureResourceRepository infrastructureRepository;

    // Constructor Injection kurali
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  BoothAreaRepository boothAreaRepository,
                                  InfrastructureResourceRepository infrastructureRepository) {
        this.reservationRepository = reservationRepository;
        this.boothAreaRepository = boothAreaRepository;
        this.infrastructureRepository = infrastructureRepository;
    }

    @Override
    @Transactional // Hocanin kurali: Veriyi degistiren islemlerde transactional
    public Reservation createReservation(Reservation reservation) {
        // 1. Stant var mi kontrol et
        BoothArea booth = boothAreaRepository.findById(reservation.getBoothArea().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Secilen stant bulunamadi. ID: " + reservation.getBoothArea().getId()));

        // 2. Tarih mantigi kontrolu
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            throw new BusinessRuleException("Baslangic tarihi bitis tarihinden sonra olamaz.");
        }

        // 3. CAKISMA KONTROLU (Ayni tarihte bu stant rezerve edilmis mi?)
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(
                booth.getId(), reservation.getStartDate(), reservation.getEndDate());
        if (!conflicts.isEmpty()) {
            throw new BusinessRuleException("Secilen stant bu tarihler arasinda baska bir sirket tarafindan rezerve edilmis.");
        }

        // 4. KESTIRIMSEL ALTYAPI VE ANOMALI YONETIMI (Elektrik ve Ag Kapasite Asimi Kontrolu)
        InfrastructureResource infrastructure = infrastructureRepository.findByHallName(booth.getHallName())
                .orElseThrow(() -> new ResourceNotFoundException("Salona ait altyapi kaynagi tanimli degil: " + booth.getHallName()));

        // Elektrik Sebekesi Asiri Yuklenme Riski Kontrolu
        if (infrastructure.getAllocatedPowerKw() + reservation.getRequestedPowerKw() > infrastructure.getMaxPowerCapacityKw()) {
            throw new BusinessRuleException(String.format(
                    "Altyapi Uyari: %s icin elektrik trafo kapasitesi asilacak! Mevcut: %.1f kW, Talep: %.1f kW, Limit: %.1f kW",
                    booth.getHallName(), infrastructure.getAllocatedPowerKw(), reservation.getRequestedPowerKw(), infrastructure.getMaxPowerCapacityKw()));
        }

        // Internet Port Limiti Kontrolu
        if (infrastructure.getUsedNetworkPorts() + reservation.getRequestedNetworkPorts() > infrastructure.getTotalNetworkPorts()) {
            throw new BusinessRuleException("Altyapi Uyari: Yetersiz ag portu! Salondaki tum internet portlari dolmak uzere.");
        }

        // Her sey uygunsa altyapi yukunu guncelle ve rezervasyonu onayla
        infrastructure.setAllocatedPowerKw(infrastructure.getAllocatedPowerKw() + reservation.getRequestedPowerKw());
        infrastructure.setUsedNetworkPorts(infrastructure.getUsedNetworkPorts() + reservation.getRequestedNetworkPorts());
        infrastructureRepository.update(infrastructure);

        reservation.setBoothArea(booth);
        reservation.setStatus(ReservationStatus.APPROVED);
        reservation.setId(null); // Gelen id'yi guvenlik icin yok say
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rezervasyon bulunamadi. ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByCompany(Long companyId) {
        return reservationRepository.findByCompanyId(companyId);
    }

    @Override
    @Transactional
    public void cancelReservation(Long id) {
        Reservation res = getReservationById(id);

        // Iptal edilince salonun altyapi yukunu geri dusur
        InfrastructureResource infrastructure = infrastructureRepository.findByHallName(res.getBoothArea().getHallName()).orElse(null);
        if (infrastructure != null) {
            infrastructure.setAllocatedPowerKw(Math.max(0, infrastructure.getAllocatedPowerKw() - res.getRequestedPowerKw()));
            infrastructure.setUsedNetworkPorts(Math.max(0, infrastructure.getUsedNetworkPorts() - res.getRequestedNetworkPorts()));
            infrastructureRepository.update(infrastructure);
        }

        res.setStatus(ReservationStatus.REJECTED);
        reservationRepository.update(res);
    }
}