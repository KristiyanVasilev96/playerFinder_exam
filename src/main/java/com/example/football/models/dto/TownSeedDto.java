package com.example.football.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class TownSeedDto {
    @Expose
    @XmlElement
    private String name;
    @Expose
    private int population;
    @Expose
    private String travelGuide;

    public TownSeedDto() {
    }

    @Size(min = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PositiveOrZero
    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Size(min = 10)
    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }
}
