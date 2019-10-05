package com.codeoftheweb.salvo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy="native")
    private Integer id;

    private LocalDate creationDate;
    @OneToMany(mappedBy="game",cascade = CascadeType.MERGE)
    private List<GamePlayer> gamePlayers;
    public Game(Integer id, LocalDate creationDate) {
        this.id = id;
        this.creationDate = creationDate;
    }
    public Game( LocalDate creationDate) {
       this.creationDate=creationDate;
    }

    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(List<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public Game(LocalDate creationDate, List<GamePlayer> gamePlayers) {
        this.creationDate = creationDate;
        this.gamePlayers = gamePlayers;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }



    public Game() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }




    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                '}';
    }
}
