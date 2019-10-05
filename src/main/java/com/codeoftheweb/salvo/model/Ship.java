package com.codeoftheweb.salvo.model;

import javax.persistence.*;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String type;
    @ManyToOne(fetch = FetchType.EAGER)
    private GamePlayer gamePlayer;

    public Ship(Integer id, GamePlayer gamePlayer) {
        this.id = id;
        this.gamePlayer = gamePlayer;
    }

    public Ship() {
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
                ", gamePlayer=" + gamePlayer +
                '}';
    }
}

