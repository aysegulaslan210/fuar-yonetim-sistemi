package com.fuar.yonetim.service;

import com.fuar.yonetim.entity.BoothArea;
import java.util.List;

public interface BoothAreaService {
    BoothArea createBoothArea(BoothArea boothArea);
    BoothArea getBoothAreaById(Long id);
    List<BoothArea> getAllBoothAreas();
    List<BoothArea> getAvailableBoothAreas(String hallName);
    BoothArea updateBoothArea(Long id, BoothArea updatedBoothArea);
    void deleteBoothArea(Long id);
}
