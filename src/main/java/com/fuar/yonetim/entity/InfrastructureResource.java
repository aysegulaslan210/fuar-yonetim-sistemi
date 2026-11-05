package com.fuar.yonetim.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "infrastructure_resources")
public class InfrastructureResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hall_name", nullable = false, unique = true, length = 100)
    private String hallName; // Ornek: "A Salonu"

    @Column(name = "max_power_capacity_kw", nullable = false)
    private Double maxPowerCapacityKw; // Salondaki trafonun maksimum guc limiti (kW)

    @Column(name = "allocated_power_kw", nullable = false)
    private Double allocatedPowerKw = 0.0; // Su ana kadar baglanan toplam guc (kW)

    @Column(name = "total_network_ports", nullable = false)
    private Integer totalNetworkPorts; // Salondaki toplam internet portu

    @Column(name = "used_network_ports", nullable = false)
    private Integer usedNetworkPorts = 0; // Kullanilan internet portu

    // Hocanin kurali: Parametresiz constructor
    public InfrastructureResource() {
    }

    public InfrastructureResource(String hallName, Double maxPowerCapacityKw, Double allocatedPowerKw, Integer totalNetworkPorts, Integer usedNetworkPorts) {
        this.hallName = hallName;
        this.maxPowerCapacityKw = maxPowerCapacityKw;
        this.allocatedPowerKw = allocatedPowerKw;
        this.totalNetworkPorts = totalNetworkPorts;
        this.usedNetworkPorts = usedNetworkPorts;
    }

    // Getter ve Setter metotlari
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Double getMaxPowerCapacityKw() {
        return maxPowerCapacityKw;
    }

    public void setMaxPowerCapacityKw(Double maxPowerCapacityKw) {
        this.maxPowerCapacityKw = maxPowerCapacityKw;
    }

    public Double getAllocatedPowerKw() {
        return allocatedPowerKw;
    }

    public void setAllocatedPowerKw(Double allocatedPowerKw) {
        this.allocatedPowerKw = allocatedPowerKw;
    }

    public Integer getTotalNetworkPorts() {
        return totalNetworkPorts;
    }

    public void setTotalNetworkPorts(Integer totalNetworkPorts) {
        this.totalNetworkPorts = totalNetworkPorts;
    }

    public Integer getUsedNetworkPorts() {
        return usedNetworkPorts;
    }

    public void setUsedNetworkPorts(Integer usedNetworkPorts) {
        this.usedNetworkPorts = usedNetworkPorts;
    }
}