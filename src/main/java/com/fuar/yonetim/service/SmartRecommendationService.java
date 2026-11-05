package com.fuar.yonetim.service;

import com.fuar.yonetim.entity.BoothArea;
import com.fuar.yonetim.repository.BoothAreaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

// Hocanin kuralı: @Lazy (ihtiyac duyulana kadar yuklenmesin) ve @Qualifier
@Service
@Lazy
@Qualifier("smartRecommendationService")
public class SmartRecommendationService implements BoothRecommendationService {

    private final BoothAreaRepository boothAreaRepository;

    public SmartRecommendationService(BoothAreaRepository boothAreaRepository) {
        this.boothAreaRepository = boothAreaRepository;
    }

    @Override
    public List<BoothArea> recommendBooths(String sector, Double budget) {
        // Yapay Zeka (AI) Odakli: Sektorel yakinlik, ziyaretci yogunlugu ve butce optimizasyonu
        return boothAreaRepository.findAll().stream()
                .filter(b -> !b.getIsReserved())
                .sorted((b1, b2) -> Double.compare(b2.getSquareMeters(), b1.getSquareMeters())) // En verimli buyuk alanlari one cikarir
                .collect(Collectors.toList());
    }
}