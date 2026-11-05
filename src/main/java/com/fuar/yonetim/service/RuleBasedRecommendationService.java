package com.fuar.yonetim.service;

import com.fuar.yonetim.entity.BoothArea;
import com.fuar.yonetim.repository.BoothAreaRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

// Hocanin kurali: Varsayilan bean olarak @Primary
@Service
@Primary
public class RuleBasedRecommendationService implements BoothRecommendationService {

    private final BoothAreaRepository boothAreaRepository;

    public RuleBasedRecommendationService(BoothAreaRepository boothAreaRepository) {
        this.boothAreaRepository = boothAreaRepository;
    }

    @Override
    public List<BoothArea> recommendBooths(String sector, Double budget) {
        // Kural tabanli: Butceye uygun ve bos stantlari dondurur
        return boothAreaRepository.findAll().stream()
                .filter(b -> !b.getIsReserved())
                .filter(b -> b.getDailyBasePrice().doubleValue() <= budget)
                .collect(Collectors.toList());
    }
}