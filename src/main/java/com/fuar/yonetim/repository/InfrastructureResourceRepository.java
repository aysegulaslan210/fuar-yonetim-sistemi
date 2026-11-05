package com.fuar.yonetim.repository;

import com.fuar.yonetim.entity.InfrastructureResource;
import java.util.List;
import java.util.Optional;

public interface InfrastructureResourceRepository {
    InfrastructureResource save(InfrastructureResource resource);
    Optional<InfrastructureResource> findById(Long id);
    Optional<InfrastructureResource> findByHallName(String hallName);
    List<InfrastructureResource> findAll();
    InfrastructureResource update(InfrastructureResource resource);
    void deleteById(Long id);
}