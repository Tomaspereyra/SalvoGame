package com.codeoftheweb.salvo.model;

import javax.persistence.*;
import java.util.List;
@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    private GamePlayer gamePlayer;

    private Integer turnNumber;
    @OneToMany(mappedBy = "salvo",cascade = CascadeType.MERGE)
    private List<SalvoLocation> salvoLocations;

    public Salvo(GamePlayer gamePlayer, Integer turnNumber, List<SalvoLocation> salvoLocations) {
        this.gamePlayer = gamePlayer;
        this.turnNumber = turnNumber;
        this.salvoLocations = salvoLocations;
    }

    public Salvo() {
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

    public Integer getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Integer turnNumber) {
        this.turnNumber = turnNumber;
    }

    public List<SalvoLocation> getSalvoLocations() {
        return salvoLocations;
    }

    public void setSalvoLocations(List<SalvoLocation> salvoLocations) {
        this.salvoLocations = salvoLocations;
    }

    @Override
    public String toString() {
        return "Salvo{" +
                "id=" + id +
                ", gamePlayer=" + gamePlayer +
                ", turnNumber=" + turnNumber +
                ", salvoLocations=" + salvoLocations +
                '}';
    }
}
