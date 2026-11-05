package com.fuar.yonetim.service;

import com.fuar.yonetim.entity.BoothArea;
import java.util.List;

public interface BoothRecommendationService {
    List<BoothArea> recommendBooths(String sector, Double budget);
}