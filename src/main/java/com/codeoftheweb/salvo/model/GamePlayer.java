package com.codeoftheweb.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native",strategy="native")
    private Integer id;
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="gameID")
    @JsonIgnore
    private Game game;
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="playerID")
    private Player player;

    private Date date;
    @OneToMany(mappedBy = "gamePlayer", cascade = CascadeType.MERGE)
    private Set<Ship> ships;

    public GamePlayer() {
    }

    public GamePlayer(Integer id, Game game, Player player, Date date) {
        this.id = id;
        this.game = game;
        this.player = player;
        this.date = date;
    }
    public GamePlayer( Game game, Player player, Date date) {
        this.id = id;
        this.game = game;
        this.player = player;
        this.date = date;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "GamePlayer{" +
                "id=" + id +
                ", game=" + game +
                ", player=" + player +
                ", date=" + date +
                '}';
    }
}
