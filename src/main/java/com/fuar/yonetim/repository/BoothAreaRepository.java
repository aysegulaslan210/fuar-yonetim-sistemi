package com.fuar.yonetim.repository;

import com.fuar.yonetim.entity.BoothArea;
import java.util.List;
import java.util.Optional;

public interface BoothAreaRepository {
    BoothArea save(BoothArea boothArea);
    Optional<BoothArea> findById(Long id);
    List<BoothArea> findAll();
    BoothArea update(BoothArea boothArea);
    void deleteById(Long id);
    Optional<BoothArea> findByCode(String code);
    List<BoothArea> findByHallNameAndAvailability(String hallName, Boolean isReserved);
}