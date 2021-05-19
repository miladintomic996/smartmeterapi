package com.tq.smartmeterapi.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "meter_readings")
public class MeterReadings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JoinColumn(name="meter_id")
    private Meter meter;

    @Column(name = "active")
    private Boolean active = true;

    @NotNull
    @Column(name="date_changed")
    private Date dateChanged;

    public MeterReadings(Date dateCreated, Double value) {
        this.dateCreated = dateCreated;
        this.value = value;
        this.active = true;
    }

    protected MeterReadings(){}

    public Long getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }
}
