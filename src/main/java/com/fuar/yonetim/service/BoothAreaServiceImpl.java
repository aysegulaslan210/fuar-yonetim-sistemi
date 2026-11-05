package com.fuar.yonetim.service;

import com.fuar.yonetim.entity.BoothArea;
import com.fuar.yonetim.exception.BusinessRuleException;
import com.fuar.yonetim.exception.ResourceNotFoundException;
import com.fuar.yonetim.repository.BoothAreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BoothAreaServiceImpl implements BoothAreaService {

    private final BoothAreaRepository boothAreaRepository;

    // Hocanin kurali: Constructor Injection
    public BoothAreaServiceImpl(BoothAreaRepository boothAreaRepository) {
        this.boothAreaRepository = boothAreaRepository;
    }

    @Override
    @Transactional // Hocanin kurali: Veri degistiren islemlerde @Transactional
    public BoothArea createBoothArea(BoothArea boothArea) {
        if (boothAreaRepository.findByCode(boothArea.getCode()).isPresent()) {
            throw new BusinessRuleException("Bu stant kodu zaten kayitli: " + boothArea.getCode());
        }
        boothArea.setId(null); // Gelen id'yi yok say
        return boothAreaRepository.save(boothArea);
    }

    @Override
    @Transactional(readOnly = true)
    public BoothArea getBoothAreaById(Long id) {
        return boothAreaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stant alani bulunamadi. ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoothArea> getAllBoothAreas() {
        return boothAreaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoothArea> getAvailableBoothAreas(String hallName) {
        return boothAreaRepository.findByHallNameAndAvailability(hallName, false);
    }

    @Override
    @Transactional
    public BoothArea updateBoothArea(Long id, BoothArea updated) {
        BoothArea existing = getBoothAreaById(id);
        existing.setHallName(updated.getHallName());
        existing.setSquareMeters(updated.getSquareMeters());
        existing.setDailyBasePrice(updated.getDailyBasePrice());
        existing.setIsReserved(updated.getIsReserved());
        return boothAreaRepository.update(existing);
    }

    @Override
    @Transactional
    public void deleteBoothArea(Long id) {
        getBoothAreaById(id); // Varligini kontrol et (yoksa 404 firlatir)
        boothAreaRepository.deleteById(id);
    }
}