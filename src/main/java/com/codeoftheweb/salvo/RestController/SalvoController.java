package com.codeoftheweb.salvo.RestController;

import com.codeoftheweb.salvo.dto.LeaderboardDTO;
import com.codeoftheweb.salvo.dto.ResponseDto;
import com.codeoftheweb.salvo.dto.SalvoDTO;
import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.service.PlayerService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200") //  the annotation enables Cross-Origin Resource Sharing (CORS) on the server.
public class SalvoController {

        @Autowired
        private GameRepository gameRepository;
        @Autowired
        private PlayerRepository playerRepository;
        @Autowired
        private GamePlayerRepository gamePlayerRepository;
        @Autowired
        private ShipRepository shipRepository;

        @Autowired
        private SalvoRepository salvoRepository;

        @Autowired
        private PlayerService playerService;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private SalvoLocationsRepository salvoLocationsRepository;

        @RequestMapping("/players")
        @PostMapping
        public ResponseEntity<String> register(@RequestParam String first, @RequestParam String last,@RequestParam String email,@RequestParam String password,@RequestParam String username){
            if (first.isEmpty() || last.isEmpty() || email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }

            if (playerService.findByEmail(email)!=  null) {
                return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
            }

            playerService.save(first,last,email,username,passwordEncoder.encode(password));
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        private boolean isGuest(Authentication authentication) {
            return authentication == null || authentication instanceof AnonymousAuthenticationToken;
        }

        @RequestMapping("/games")
        public List<Map<String,Object>> getGames(Authentication auth){
            List<Map<String,Object>> gamesMap = new ArrayList<>();

            if(!isGuest(auth)){
                Player p = this.getUserAuthenticated(auth);
                Map<String,Object> playerMap = new HashMap<>();
                Map<String,String> playerObject = new HashMap<>();
                playerObject.put("id",p.getId().toString());
                playerObject.put("name",p.getUserName());
                playerMap.put("player",playerObject);
                gamesMap.add(playerMap);

            }

            List<Game> gamesList = this.gameRepository.findAll();
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
        private Player getUserAuthenticated(Authentication auth){
            Player player = new Player();
            if(auth !=null) {
                player = this.playerService.findByEmail(auth.getName());
            }
            return player;

        }
        @RequestMapping("/ship/save")
        @PostMapping
        public ResponseEntity<Map<String,Object>> saveShip(@RequestBody List<Ship> ships, Authentication auth){
            ResponseDto responseDto = new ResponseDto();
            Player player = getUserAuthenticated(auth);
            if (player != null){
            GamePlayer gamePlayer = gamePlayerRepository.findGamePlayerByPlayerParam(player.getId());

                gamePlayer.getShips().forEach(ship-> {
                    ship.setGamePlayer(gamePlayer);
                    shipRepository.delete(ship);});

               ships.forEach(ship -> {ship.setGamePlayer(gamePlayer);});
               gamePlayer.setShips(ships);
               gamePlayerRepository.save(gamePlayer);
               responseDto.setCod("201");
               responseDto.setStatus("Created");

            }
            else{
                responseDto.setCod("401");
                responseDto.setStatus("Unauthorized");
            }

            Map<String,Object> response = new HashMap<>();
            response.put("data",responseDto.getData());
            response.put("status",responseDto.getStatus());
            return new ResponseEntity(response,HttpStatus.OK);
        }
        @RequestMapping("/salvo/save")
        @PostMapping
        public ResponseEntity<Map<String,Object>> saveSalvoes(@RequestBody List<Salvo> salvoes, Authentication auth){
            ResponseDto responseDto = new ResponseDto();
            Player player = getUserAuthenticated(auth);
            if (player != null){
                GamePlayer gamePlayer = gamePlayerRepository.findGamePlayerByPlayerParam(player.getId());
                gamePlayer.getSalvoes().forEach(salvo->{
                    salvo.setGamePlayer(gamePlayer);
                    salvo.getSalvoLocations().forEach(salvoLoc->{
                        salvoLocationsRepository.delete(salvoLoc);
                    });
                    salvoRepository.delete(salvo);});

                salvoes.forEach(salvo->{
                    salvo.setGamePlayer(gamePlayer);
                    salvoRepository.save(salvo);
                    salvo.getSalvoLocations().forEach(salvoLoc->{
                        salvoLoc.setSalvo(salvo);
                        this.salvoLocationsRepository.save(salvoLoc);
                    });


                });

                gamePlayer.setSalvoes(salvoes);



                gamePlayerRepository.save(gamePlayer);
                responseDto.setCod("201");
                responseDto.setStatus("Created");



            }else{
                responseDto.setCod("401");
                responseDto.setStatus("Unauthorized");
            }
            Map<String,Object> response = new HashMap<>();
            response.put("data",responseDto.getData());
            response.put("status",responseDto.getStatus());

            return new ResponseEntity(response,HttpStatus.OK);


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
        @RequestMapping("/createGame")
        @PostMapping
        public ResponseEntity<Map<String,Object>> createdGame(Authentication auth){
            Player player = this.getUserAuthenticated(auth);
            ResponseEntity response = status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
            if(player != null ){
                try {
                    Game game1 = gameRepository.save(new Game(LocalDate.parse("2011-08-03T03:15:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME), new ArrayList<GamePlayer>()));
                    gamePlayerRepository.save(new GamePlayer(game1, player, new Date(), shipRepository.findAll()));
                    response = status(HttpStatus.CREATED).body(game1);
                }
                catch(Exception e){
                    response = status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear un juego");

                }

            }
            return response;


        }
        @RequestMapping("/join")
        @PostMapping
        public ResponseEntity<Map<String,Object>> joinGame(@RequestBody Map<String,String> body,Authentication auth){
            System.out.println(body.get("id"));
            ResponseEntity response = status(HttpStatus.OK).body(body);
            try{
            Game game = gameRepository.getOne(Integer.valueOf(body.get("id")));
            Player player = getUserAuthenticated(auth);
            System.out.println(player);
            gamePlayerRepository.save(new GamePlayer(game, player, new Date(), shipRepository.findAll()));

            }
            catch (Exception e){
                response = status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error inesperado al intentar unirse a un juego");
                                    }
            return response;

        }

        @RequestMapping("ship/{idgame}")
        public ResponseEntity<Map<String, Object>> getShipByGamePlayer(@PathVariable Integer idgame, Authentication auth){
            Game game = this.gameRepository.getOne(idgame);
            Player player = this.getUserAuthenticated(auth);
            Map<String,Object> response = new HashMap<>();
            ResponseDto responsedto = new ResponseDto();
            for(GamePlayer g : game.getGamePlayers()){
                if(g.getPlayer().equals(player)){
                    responsedto.setCod("200");
                    responsedto.setData(g.getShips());
                    response.put("data",g.getShips());

                }
            }
            if(responsedto.getCod()!= "200"){
                responsedto.setCod("502");
                responsedto.setStatus("Player not found in current game");
            }
            System.out.println(responsedto.getData());
            response.put("cod",responsedto.getCod());
            response.put("data",responsedto.getData());
            response.put("status",responsedto.getStatus());
            return new ResponseEntity(response,HttpStatus.OK);

        }
        @RequestMapping("/salvo/{idgame}")
        public ResponseEntity<Map<String,Object>> getSalvoByGamePlayer(@PathVariable Integer idgame, Authentication auth){


           Game game = this.gameRepository.getOne(idgame);
            Player player = this.getUserAuthenticated(auth);
            Map<String,Object> response = new HashMap<>();
            ResponseDto responsedto = new ResponseDto();
            for(GamePlayer g : game.getGamePlayers()){
                if(g.getPlayer().equals(player)){
                    responsedto.setCod("200");
                    responsedto.setData(g.getSalvoes());
                }
            }
            if(responsedto.getCod()!= "200"){
                responsedto.setCod("502");
                responsedto.setStatus("Player not found in current game");
            }

            response.put("cod",responsedto.getCod());
            response.put("data",responsedto.getData());
            response.put("status",responsedto.getStatus());
            return new ResponseEntity(response,HttpStatus.OK);

        }


        @RequestMapping("/game_view/{id}")
        public ResponseEntity<Map<String, Object>> getGameView(@PathVariable Integer id){
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
           return  status(HttpStatus.OK).body(gameMap);

        }
}
