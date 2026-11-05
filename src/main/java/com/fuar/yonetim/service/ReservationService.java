package com.fuar.yonetim.service;

import com.fuar.yonetim.entity.Reservation;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(Reservation reservation);
    Reservation getReservationById(Long id);
    List<Reservation> getAllReservations();
    List<Reservation> getReservationsByCompany(Long companyId);
    void cancelReservation(Long id);
}