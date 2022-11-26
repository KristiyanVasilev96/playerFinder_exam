package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stats")
public class StatsSeedRootDto {

    @XmlElement(name = "stat")
    private List<StatsSeedDto> stat;

    public StatsSeedRootDto() {
    }

    public List<StatsSeedDto> getStat() {
        return stat;
    }

    public void setStat(List<StatsSeedDto> stat) {
        this.stat = stat;
    }
}
