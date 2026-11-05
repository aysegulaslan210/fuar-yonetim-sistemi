package com.fuar.yonetim.repository;

import com.fuar.yonetim.entity.Reservation;
import com.fuar.yonetim.entity.ReservationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    List<Reservation> findAll();
    Reservation update(Reservation reservation);
    void deleteById(Long id);
    List<Reservation> findByCompanyId(Long companyId);
    List<Reservation> findByStatus(ReservationStatus status);

    // Hocanin kurali: Cakisma kontrolu icin ozel JPQL filtre sorgusu
    List<Reservation> findConflictingReservations(Long boothAreaId, LocalDate startDate, LocalDate endDate);
}