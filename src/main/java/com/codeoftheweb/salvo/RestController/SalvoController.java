package com.codeoftheweb.salvo.RestController;

import com.codeoftheweb.salvo.dto.LeaderboardDTO;
import com.codeoftheweb.salvo.dto.SalvoDTO;
import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.GameRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.service.impl.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.xml.ws.Response;
import java.util.*;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerServiceImpl playerService;
    @RequestMapping(path="/login",method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestParam String email, @RequestParam String password, Authentication authentication){
       // System.out.println(authentication.getName());
        System.out.println(authentication);
        if(email.isEmpty() || password.isEmpty()){
            return  new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);


        }
        if(playerService.findByEmail(email) != null && playerService.findByEmail(email).getPassword().equals(password)){
            return new ResponseEntity<>("Logged in ", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("bad data", HttpStatus.BAD_REQUEST);
        }




    }

    @RequestMapping("/games")
    public List<Map<String,Object>> getGames(){
        List<Game> gamesList = this.gameRepository.findAll();
        List<Map<String,Object>> gamesMap = new ArrayList<>();
        //meti el new map adentro del for por que me pasaba esto https://stackoverflow.com/questions/4100486/java-create-a-list-of-hashmaps
        List<Map<String,Object>> gamePlayerMap = new ArrayList<>();

        for(Game g:gamesList){
            Map<String,Object> gameMap = new LinkedHashMap<>();
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
            gameMap.put("scores:",g.getScores());
            gamePlayerMap = new ArrayList<>();
            gamesMap.add(gameMap);


        }
        return gamesMap;


    }
    @RequestMapping("/leaderboard")
    public List<Map<String,Object>>getLeaderBoard(){
        List<Map<String,Object>> leaderboardList = new ArrayList<>();

        for(Player player: playerService.findAll()){
            Map<String,Object> leaderboardMap = new LinkedHashMap<>();
            leaderboardMap.put("player",player.getEmailAddress());
            LeaderboardDTO leaderboardDTO = new LeaderboardDTO();
            leaderboardDTO.setTotalWins(playerService.totalWins(player));
            leaderboardDTO.setTotalLosses(playerService.totalLosses(player));
            leaderboardDTO.setTotalTies(playerService.totalTies(player));
            leaderboardDTO.setTotalScore(playerService.totalScore(player));
            leaderboardMap.put("leaderBoard",leaderboardDTO);
            leaderboardList.add(leaderboardMap);



        }

        return leaderboardList;
    }

    @RequestMapping("/game_view/{id}")
    public Map<String,Object> getGameView(@PathVariable Integer id){
        Game game = this.gameRepository.getOne(id);
        Map<String,Object> gameMap= new LinkedHashMap<>(); // ordena los elementos segun se van cargando
        gameMap.put("id",game.getId());
        gameMap.put("created",game.getCreationDate());
        List<Map<String,Object>>gamePlayersList  = new ArrayList<>(); // lista para los GamePlayers
        List<Map<String,Object>> shipsList = new ArrayList<>();
        List<SalvoDTO> salvoJson = new ArrayList<>();

        for(GamePlayer gamePlayer:game.getGamePlayers()){
            Map<String,Object> gamePlayerMap = new HashMap<>(); //map para cada game Player
            gamePlayerMap.put("id",gamePlayer.getId());
            Map<String,Object> playerMap = new HashMap<>();
            playerMap.put("id",gamePlayer.getPlayer().getId());
            playerMap.put("email",gamePlayer.getPlayer().getEmailAddress());
            gamePlayerMap.put("player",playerMap);
            gamePlayersList.add(gamePlayerMap); // lo agrego a la lista
            /* mappeo ships */
            for(Ship ship :gamePlayer.getShips()){
                Map<String,Object> shipMap = new HashMap<>();
                shipMap.put("type", ship.getType());
                shipMap.put("locations",ship.getLocationsList());
                shipsList.add(shipMap);
            }
            //--mapeo salvo  -----//
            for(Salvo salvo: gamePlayer.getSalvoes()){
                SalvoDTO salvoDTO = new SalvoDTO();
                salvoDTO.setTurn(salvo.getTurnNumber());
                salvoDTO.setPlayerID(gamePlayer.getPlayer().getId());
                List<String> cells = new ArrayList<>();
                for (SalvoLocation salvoLoc:salvo.getSalvoLocations()){
                    cells.add(salvoLoc.getCell());
                }
                salvoDTO.setLocations(cells);

                salvoJson.add(salvoDTO);

            }

        }
        gameMap.put("gamePlayers",gamePlayersList); // lo agrego al map general
        gameMap.put("ships",shipsList);
        gameMap.put("salvoes",salvoJson);

        return gameMap;

    }
}
