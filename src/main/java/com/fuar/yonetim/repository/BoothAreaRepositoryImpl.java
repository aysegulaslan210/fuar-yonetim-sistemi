package com.fuar.yonetim.repository;

import com.fuar.yonetim.entity.BoothArea;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class BoothAreaRepositoryImpl implements BoothAreaRepository {

    private final EntityManager entityManager;

    public BoothAreaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public BoothArea save(BoothArea boothArea) {
        entityManager.persist(boothArea);
        return boothArea;
    }

    @Override
    public Optional<BoothArea> findById(Long id) {
        return Optional.ofNullable(entityManager.find(BoothArea.class, id));
    }

    @Override
    public List<BoothArea> findAll() {
        return entityManager.createQuery("SELECT b FROM BoothArea b", BoothArea.class)
                .getResultList();
    }

    @Override
    public BoothArea update(BoothArea boothArea) {
        return entityManager.merge(boothArea);
    }

    @Override
    public void deleteById(Long id) {
        BoothArea boothArea = entityManager.find(BoothArea.class, id);
        if (boothArea != null) {
            entityManager.remove(boothArea);
        }
    }

    @Override
    public Optional<BoothArea> findByCode(String code) {
        TypedQuery<BoothArea> query = entityManager.createQuery(
                "SELECT b FROM BoothArea b WHERE b.code = :code", BoothArea.class);
        query.setParameter("code", code);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<BoothArea> findByHallNameAndAvailability(String hallName, Boolean isReserved) {
        TypedQuery<BoothArea> query = entityManager.createQuery(
                "SELECT b FROM BoothArea b WHERE b.hallName = :hallName AND b.isReserved = :isReserved",
                BoothArea.class);
        query.setParameter("hallName", hallName);
        query.setParameter("isReserved", isReserved);
        return query.getResultList();
    }
}