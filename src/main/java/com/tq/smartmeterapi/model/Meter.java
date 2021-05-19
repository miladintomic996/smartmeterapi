package com.tq.smartmeterapi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "meter")
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="serial_number",unique=true)
    private Integer serialNumber;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "active")
    private Boolean active= true;


    @OneToMany(mappedBy = "meter")
    private List<MeterReadings> meterReadings;


    public Meter(Integer serialNumber) {
        this.serialNumber = serialNumber;
        this.active = true;
    }

    protected Meter(){}

    public Long getId() {
        return id;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<MeterReadings> getMeterReadings() {
        return meterReadings;
    }

    public void setMeterReadings(List<MeterReadings> meterReadings) {
        this.meterReadings = meterReadings;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
