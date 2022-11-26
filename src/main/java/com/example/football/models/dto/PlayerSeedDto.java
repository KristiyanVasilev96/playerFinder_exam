package com.example.football.models.dto;

import com.example.football.models.entity.Position;
import com.google.gson.annotations.Expose;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDto {

    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "birth-date")
    private String birthDate;
    @XmlElement(name = "position")
    private Position position;

    @XmlElement(name = "town")
    @Expose
    private TownSeedDto townName;

    @XmlElement(name = "team")
    @Expose
    private TeamSeedDto teamName;

    @XmlElement(name = "stat")
    private StatIdDto id;

    public PlayerSeedDto() {
    }

    @Size(min = 2)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Size(min = 2)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Enumerated(EnumType.STRING)
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public TownSeedDto getTownName() {
        return townName;
    }

    public void setTownName(TownSeedDto townName) {
        this.townName = townName;
    }

    public TeamSeedDto getTeamName() {
        return teamName;
    }

    public void setTeamName(TeamSeedDto teamName) {
        this.teamName = teamName;
    }

    public StatIdDto getId() {
        return id;
    }

    public void setId(StatIdDto id) {
        this.id = id;
    }
}
