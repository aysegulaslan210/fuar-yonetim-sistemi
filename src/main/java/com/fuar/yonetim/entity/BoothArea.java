package com.fuar.yonetim.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "booth_areas")
public class BoothArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code; // Örn: "HALL-A-101"

    @Column(name = "hall_name", nullable = false, length = 100)
    private String hallName; // Örn: "A Salonu"

    @Column(name = "square_meters", nullable = false)
    private Double squareMeters; // Metrekare büyüklüğü

    @Column(name = "daily_base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyBasePrice; // Günlük baz kiralama ücreti

    @Column(name = "is_reserved", nullable = false)
    private Boolean isReserved = false; // Rezervasyon durumu

    // 1. Hocanın Şartı: Parametresiz constructor
    public BoothArea() {
    }

    // Parametreli constructor (kolay nesne üretmek için)
    public BoothArea(String code, String hallName, Double squareMeters, BigDecimal dailyBasePrice, Boolean isReserved) {
        this.code = code;
        this.hallName = hallName;
        this.squareMeters = squareMeters;
        this.dailyBasePrice = dailyBasePrice;
        this.isReserved = isReserved;
    }

    // Getter ve Setter Metotları
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Double getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(Double squareMeters) {
        this.squareMeters = squareMeters;
    }

    public BigDecimal getDailyBasePrice() {
        return dailyBasePrice;
    }

    public void setDailyBasePrice(BigDecimal dailyBasePrice) {
        this.dailyBasePrice = dailyBasePrice;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Boolean isReserved) {
        this.isReserved = isReserved;
    }
}