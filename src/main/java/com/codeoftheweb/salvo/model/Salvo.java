package com.codeoftheweb.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy="native")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayerID")
    @JsonIgnore
    private GamePlayer gamePlayer;

    private Integer turnNumber;
    @OneToMany(mappedBy="salvo",cascade = CascadeType.MERGE)
    private List<SalvoLocation> salvoLocations;

    public Salvo(GamePlayer gamePlayer, Integer turnNumber, List<SalvoLocation> salvoLocations) {
        this.gamePlayer = gamePlayer;
        this.turnNumber = turnNumber;
        this.salvoLocations = salvoLocations;
    }

    public Salvo(GamePlayer gamePlayer, Integer turnNumber) {
        this.gamePlayer = gamePlayer;
        this.turnNumber = turnNumber;
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
                ", turnNumber=" + turnNumber +
                ", salvoLocations=" + salvoLocations +
                '}';
    }
}
