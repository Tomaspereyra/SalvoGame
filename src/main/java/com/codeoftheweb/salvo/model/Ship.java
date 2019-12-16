package com.codeoftheweb.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayerID")
    @JsonIgnore
    private GamePlayer gamePlayer;
    @ElementCollection
    @Column(name="location")
    private List<ShipLocations> locationsList;

    public Ship(Integer id, GamePlayer gamePlayer) {
        this.id = id;
        this.gamePlayer = gamePlayer;
    }

    public Ship(String type, GamePlayer gamePlayer) {
        this.type = type;
        this.gamePlayer = gamePlayer;
    }

    public Ship() {
    }

    public Ship(String type, GamePlayer gamePlayer, List<ShipLocations> locationsList) {
        this.type = type;
        this.gamePlayer = gamePlayer;
        this.locationsList = locationsList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ShipLocations> getLocationsList() {
        return locationsList;
    }

    public void setLocationsList(List<ShipLocations> locationsList) {
        this.locationsList = locationsList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", type='" + type + '\'' +

                ", locationsList=" + locationsList +
                '}';
    }
}

