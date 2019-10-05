package com.codeoftheweb.salvo.RestController;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.repository.GameRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/games")
    public List<Map<String,Object>> getIds(){
        List<Game> gamesList = this.gameRepository.findAll();
        List<Map<String,Object>> gamesMap = new ArrayList<>();
        //meti el new map adentro del for por que me pasaba esto https://stackoverflow.com/questions/4100486/java-create-a-list-of-hashmaps
        List<Map<String,Object>> gamePlayerMap = new ArrayList<>();

        for(Game g:gamesList){
            Map<String,Object> gameMap = new HashMap<>();
            gameMap.put("id",g.getId());
            gameMap.put("created",g.getCreationDate());
            for(GamePlayer gamePlayer: g.getGamePlayers()){
                Map<String,Object> gamesPlayers = new HashMap<>();
                gamesPlayers.put("id",gamePlayer.getId());
                Map<String,Object> player = new HashMap<>();
                player.put("id",gamePlayer.getPlayer().getId());
                player.put("email",gamePlayer.getPlayer().getEmailAddress());
                gamesPlayers.put("player",player);
                gamePlayerMap.add(gamesPlayers);
            }
            gameMap.put("gamePlayers",gamePlayerMap);
            gamePlayerMap = new ArrayList<>();
            gamesMap.add(gameMap);


        }
        return gamesMap;


    }
}
