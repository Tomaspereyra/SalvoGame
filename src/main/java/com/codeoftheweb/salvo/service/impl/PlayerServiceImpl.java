package com.codeoftheweb.salvo.service.impl;

import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Score;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Double totalScore(Player player) {
        Double total=0.0;
        for(Score score:player.getScores()){
            total+=score.getScore();
        }
        return total;
    }

    @Override
    public Integer totalWins(Player player) {
        Integer totalWins = 0;
        for(Score score:player.getScores()){
            if(score.getScore()==1.5){
                totalWins+=1;
            }

        }
        return totalWins;
    }

    @Override
    public Integer totalLosses(Player player) {
        Integer totalLosses = 0;
        for(Score score:player.getScores()){
            if(score.getScore()==0){
                totalLosses+=1;
            }
        }
        return totalLosses;
    }

    @Override
    public Integer totalTies(Player player) {
        Integer totalTies =0;
        for(Score score:player.getScores()){
            if(score.getScore()==0.5){
                totalTies+=1;
            }
        }
        return totalTies;
    }
}
