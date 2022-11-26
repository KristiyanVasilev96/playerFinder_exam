package com.example.football.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "stats")
public class Stat extends BaseEntity{
    private double shooting;
    private double passing;
    private double endurance;

    public Stat() {
    }

    @Column(nullable = false)
    public double getShooting() {
        return shooting;
    }

    public void setShooting(double shooting) {
        this.shooting = shooting;
    }
    @Column(nullable = false)
    public double getPassing() {
        return passing;
    }

    public void setPassing(double passing) {
        this.passing = passing;
    }
    @Column(nullable = false)
    public double getEndurance() {
        return endurance;
    }

    public void setEndurance(double endurance) {
        this.endurance = endurance;
    }
}
