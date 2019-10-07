package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Ship;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.GameRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.repository.ShipRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepo, ShipRepository shipRepository){
		return (args)->{


					playerRepository.save(new Player("Jack", "Bauer", "j.bauer", "j.bauer@ctu.gov","24"));
					playerRepository.save(new Player("Chloe", "O'Brian", "c.obrian", "c.obrian@ctu.gov","42"));
					playerRepository.save(new Player("Kim", "Bauer", "kim_bauer", "kim_bauer@gmail.com","kb"));
					playerRepository.save(new Player("Tony", "Almeida", "t.almeida", "t.almeida@ctu.gov","mole"));
					gameRepository.save(new Game(LocalDate.parse("2011-08-03T03:15:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME),new ArrayList<GamePlayer>()));
					gameRepository.save(new Game(LocalDate.parse("2011-08-03T04:15:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME), new ArrayList<GamePlayer>()));
					gameRepository.save(new Game(LocalDate.parse("2011-08-03T05:15:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME),new ArrayList<GamePlayer>()));


			  		gamePlayerRepo.save(new GamePlayer(gameRepository.findById(1).get(),playerRepository.findById(1).get(), new Date(),shipRepository.findAll()));
					gamePlayerRepo.save(new GamePlayer(gameRepository.findById(1).get(),playerRepository.findById(2).get(), new Date(),shipRepository.findAll()));
					shipRepository.save(new Ship("carrier",gamePlayerRepo.getOne(1)));
                    shipRepository.save(new Ship("Battleship",gamePlayerRepo.getOne(1)));
                    shipRepository.save(new Ship("Submarine",gamePlayerRepo.getOne(1)));
                    shipRepository.save(new Ship("Destroyer",gamePlayerRepo.getOne(1)));
                    shipRepository.save(new Ship("Patrol Boat",gamePlayerRepo.getOne(1)));
		};
	}


}
