package com.fuar.yonetim.repository;

import com.fuar.yonetim.entity.InfrastructureResource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class InfrastructureResourceRepositoryImpl implements InfrastructureResourceRepository {

    private final EntityManager entityManager;

    public InfrastructureResourceRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public InfrastructureResource save(InfrastructureResource resource) {
        entityManager.persist(resource);
        return resource;
    }

    @Override
    public Optional<InfrastructureResource> findById(Long id) {
        return Optional.ofNullable(entityManager.find(InfrastructureResource.class, id));
    }

    @Override
    public Optional<InfrastructureResource> findByHallName(String hallName) {
        TypedQuery<InfrastructureResource> query = entityManager.createQuery(
                "SELECT r FROM InfrastructureResource r WHERE r.hallName = :hallName", InfrastructureResource.class);
        query.setParameter("hallName", hallName);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<InfrastructureResource> findAll() {
        return entityManager.createQuery("SELECT r FROM InfrastructureResource r", InfrastructureResource.class)
                .getResultList();
    }

    @Override
    public InfrastructureResource update(InfrastructureResource resource) {
        return entityManager.merge(resource);
    }

    @Override
    public void deleteById(Long id) {
        InfrastructureResource resource = entityManager.find(InfrastructureResource.class, id);
        if (resource != null) {
            entityManager.remove(resource);
        }
    }
}