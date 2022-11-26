package com.example.football.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatsSeedDto {

    @XmlElement(name = "shooting")
    private double shooting;
    @XmlElement(name = "passing")
    private double passing;
    @XmlElement(name = "endurance")
    private double endurance;

    public StatsSeedDto() {
    }

    @Positive
    public double getShooting() {
        return shooting;
    }

    public void setShooting(double shooting) {
        this.shooting = shooting;
    }

    @Positive
    public double getPassing() {
        return passing;
    }

    public void setPassing(double passing) {
        this.passing = passing;
    }

    @Positive
    public double getEndurance() {
        return endurance;
    }

    public void setEndurance(double endurance) {
        this.endurance = endurance;
    }
}
