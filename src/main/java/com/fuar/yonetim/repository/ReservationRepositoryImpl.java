package com.fuar.yonetim.repository;

import com.fuar.yonetim.entity.Reservation;
import com.fuar.yonetim.entity.ReservationStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final EntityManager entityManager;

    public ReservationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Reservation save(Reservation reservation) {
        entityManager.persist(reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Reservation.class, id));
    }

    @Override
    public List<Reservation> findAll() {
        return entityManager.createQuery("SELECT r FROM Reservation r", Reservation.class)
                .getResultList();
    }

    @Override
    public Reservation update(Reservation reservation) {
        return entityManager.merge(reservation);
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation != null) {
            entityManager.remove(reservation);
        }
    }

    @Override
    public List<Reservation> findByCompanyId(Long companyId) {
        TypedQuery<Reservation> query = entityManager.createQuery(
                "SELECT r FROM Reservation r WHERE r.companyId = :companyId", Reservation.class);
        query.setParameter("companyId", companyId);
        return query.getResultList();
    }

    @Override
    public List<Reservation> findByStatus(ReservationStatus status) {
        TypedQuery<Reservation> query = entityManager.createQuery(
                "SELECT r FROM Reservation r WHERE r.status = :status", Reservation.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Reservation> findConflictingReservations(Long boothAreaId, LocalDate startDate, LocalDate endDate) {
        // Tarih araliginda ayni stantta onaylanmis rezervasyon var mi kontrol eden JPQL
        TypedQuery<Reservation> query = entityManager.createQuery(
                "SELECT r FROM Reservation r WHERE r.boothArea.id = :boothAreaId " +
                        "AND r.status = :approvedStatus " +
                        "AND ((r.startDate <= :endDate) AND (r.endDate >= :startDate))", Reservation.class);
        query.setParameter("boothAreaId", boothAreaId);
        query.setParameter("approvedStatus", ReservationStatus.APPROVED);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
}