package com.fuar.yonetim.entity;

public enum ReservationStatus {
    PENDING,   // Onay bekliyor
    APPROVED,  // Kapasite ve cakisma kontrollerini gecti, onaylandi
    REJECTED   // Trafo kapasitesi asimi veya cakisma nedeniyle reddedildi
}