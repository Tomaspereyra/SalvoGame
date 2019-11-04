package com.codeoftheweb.salvo.service;

import com.codeoftheweb.salvo.model.Player;

import java.util.List;

public interface PlayerService {

    public List<Player> findAll();
    public Double totalScore(Player player);
    public Integer totalWins(Player player);
    public Integer totalLosses(Player player);
    public Integer totalTies(Player player);
    Player findByEmail(String email);
    public Player save(String first,String last,String email,String username,String pass);

    }
