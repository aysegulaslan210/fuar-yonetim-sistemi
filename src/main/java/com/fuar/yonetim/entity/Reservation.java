package com.fuar.yonetim.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Berk'in yazacagi Company tablosunun ID'sine referans
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    // Bir stanta farkli tarihlerde birden fazla rezervasyon yapilabilir
    @ManyToOne
    @JoinColumn(name = "booth_area_id", nullable = false)
    private BoothArea boothArea;

    @Column(name = "requested_power_kw", nullable = false)
    private Double requestedPowerKw; // Talep edilen elektrik gucu (kW)

    @Column(name = "requested_network_ports", nullable = false)
    private Integer requestedNetworkPorts; // Talep edilen internet port sayisi

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ReservationStatus status = ReservationStatus.PENDING;

    // Hocanin kurali: Parametresiz constructor
    public Reservation() {
    }

    public Reservation(Long companyId, BoothArea boothArea, Double requestedPowerKw, Integer requestedNetworkPorts, LocalDate startDate, LocalDate endDate, ReservationStatus status) {
        this.companyId = companyId;
        this.boothArea = boothArea;
        this.requestedPowerKw = requestedPowerKw;
        this.requestedNetworkPorts = requestedNetworkPorts;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getter ve Setter metotlari
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public BoothArea getBoothArea() {
        return boothArea;
    }

    public void setBoothArea(BoothArea boothArea) {
        this.boothArea = boothArea;
    }

    public Double getRequestedPowerKw() {
        return requestedPowerKw;
    }

    public void setRequestedPowerKw(Double requestedPowerKw) {
        this.requestedPowerKw = requestedPowerKw;
    }

    public Integer getRequestedNetworkPorts() {
        return requestedNetworkPorts;
    }

    public void setRequestedNetworkPorts(Integer requestedNetworkPorts) {
        this.requestedNetworkPorts = requestedNetworkPorts;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}