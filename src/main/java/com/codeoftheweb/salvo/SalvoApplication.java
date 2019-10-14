package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
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
	public CommandLineRunner initData(SalvoLocationsRepository salvoLocationsRepository,SalvoRepository salvoRepository,PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepo, ShipRepository shipRepository){
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

					List<ShipLocations> locations = new ArrayList<>();
					locations.add(new ShipLocations("H2"));
					locations.add(new ShipLocations("H3"));
					locations.add(new ShipLocations("H4"));



					shipRepository.save(new Ship("Destroyer",gamePlayerRepo.getOne(1),locations));
					locations.clear();
					locations.add(new ShipLocations("E1"));
					locations.add(new ShipLocations("F1"));
					locations.add(new ShipLocations("G1"));

					shipRepository.save(new Ship("Submarine",gamePlayerRepo.getOne(1),locations));
					locations.clear();
					locations.add(new ShipLocations("B4"));
					locations.add(new ShipLocations("B5"));
					shipRepository.save(new Ship("Patrol Boat",gamePlayerRepo.getOne(1),locations));
					locations.clear();
					shipRepository.save(new Ship("carrier",gamePlayerRepo.getOne(1)));
					shipRepository.save(new Ship("Battleship",gamePlayerRepo.getOne(1)));
                    shipRepository.save(new Ship("Patrol Boat",gamePlayerRepo.getOne(1)));
                   //-------------- game 1 - turn 1 ---------------//
                    salvoRepository.save(new Salvo(gamePlayerRepo.findById(1).get(),1));

					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.findById(1).get(),"B5"));
					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(1),"C5"));
				    salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(1),"F1"));

					salvoRepository.save(new Salvo(gamePlayerRepo.getOne(2),1));
					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(2),"B4"));
					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(2),"B5"));
					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(2),"B6"));
					//-------------game 1 -turn 2 ------------//
					salvoRepository.save(new Salvo(gamePlayerRepo.findById(1).get(),2));

					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(3),"F2"));
					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(3),"D5"));

					salvoRepository.save(new Salvo(gamePlayerRepo.getOne(2),2));
					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(4),"E1"));
					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(4),"H3"));
					salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(4),"A2"));





		};

	}


}
