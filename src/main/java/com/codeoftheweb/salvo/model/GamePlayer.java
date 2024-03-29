package com.codeoftheweb.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    private List<Ship> ships;//Pasar a Set despues de las pruebas
    @OneToMany(mappedBy = "gamePlayer",cascade = CascadeType.MERGE)
    private List<Salvo> salvoes;
    public GamePlayer() {
    }

    public GamePlayer(Game game, Player player, Date date, List<Ship> ships, List<Salvo> salvoes) {
        this.game = game;
        this.player = player;
        this.date = date;
        this.ships = ships;
        this.salvoes = salvoes;
    }

    public GamePlayer(Integer id, Game game, Player player, Date date) {
        this.id = id;
        this.game = game;
        this.player = player;
        this.date = date;
    }
    public GamePlayer( Game game, Player player, Date date) {

        this.game = game;
        this.player = player;
        this.date = date;
    }

    public GamePlayer(Game game, Player player, Date date, List<Ship> ships) {
        this.game = game;
        this.player = player;
        this.date = date;
        this.ships = ships;
    }


    public List<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(List<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GamePlayer)) return false;
        GamePlayer that = (GamePlayer) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(game, that.game) &&
                Objects.equals(player, that.player) &&
                Objects.equals(date, that.date) &&
                Objects.equals(ships, that.ships) &&
                Objects.equals(salvoes, that.salvoes);
    }


    @Override
    public String toString() {
        return "GamePlayer{" +
                "id=" + id +

                ", player=" + player +
                ", date=" + date +
                ", ships=" + ships +
                ", salvoes=" + salvoes +
                '}';
    }
}
