package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
		public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();}

		@Bean
		public CommandLineRunner initData (ScoreRepository scoreRepository, SalvoLocationsRepository
		salvoLocationsRepository, SalvoRepository salvoRepository, PlayerRepository playerRepository, GameRepository
		gameRepository, GamePlayerRepository gamePlayerRepo, ShipRepository shipRepository){
			return (args) -> {


				Player jack = playerRepository.save(new Player("Jack", "Bauer", "j.bauer", "j.bauer@ctu.gov", passwordEncoder().encode("24")));
				Player chloe = playerRepository.save(new Player("Chloe", "O'Brian", "c.obrian", "c.obrian@ctu.gov", passwordEncoder().encode("42")));
				Player kim = playerRepository.save(new Player("Kim", "Bauer", "kim_bauer", "kim_bauer@gmail.com", passwordEncoder().encode("kb")));
				Player tony = playerRepository.save(new Player("Tony", "Almeida", "t.almeida", "t.almeida@ctu.gov", passwordEncoder().encode("mole")));
				Game game1 = gameRepository.save(new Game(LocalDate.parse("2011-08-03T03:15:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME), new ArrayList<GamePlayer>()));
				Game game2 = gameRepository.save(new Game(LocalDate.parse("2011-08-03T04:15:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME), new ArrayList<GamePlayer>()));
				Game game3 = gameRepository.save(new Game(LocalDate.parse("2011-08-03T05:15:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME), new ArrayList<GamePlayer>()));


				gamePlayerRepo.save(new GamePlayer(game1, jack, new Date(), shipRepository.findAll()));
				gamePlayerRepo.save(new GamePlayer(game1, chloe, new Date(), shipRepository.findAll()));

				gamePlayerRepo.save(new GamePlayer(game2,chloe,new Date(),shipRepository.findAll()));

				List<ShipLocations> locations = new ArrayList<>();
				locations.add(new ShipLocations("H2"));
				locations.add(new ShipLocations("H3"));
				locations.add(new ShipLocations("H4"));


				shipRepository.save(new Ship("Destroyer", gamePlayerRepo.getOne(1), locations));
				locations.clear();
				locations.add(new ShipLocations("E1"));
				locations.add(new ShipLocations("F1"));
				locations.add(new ShipLocations("G1"));

				shipRepository.save(new Ship("Submarine", gamePlayerRepo.getOne(1), locations));
				locations.clear();
				locations.add(new ShipLocations("B4"));
				locations.add(new ShipLocations("B5"));
				shipRepository.save(new Ship("Patrol Boat", gamePlayerRepo.getOne(1), locations));
				locations.clear();
				shipRepository.save(new Ship("carrier", gamePlayerRepo.getOne(1)));
				shipRepository.save(new Ship("Battleship", gamePlayerRepo.getOne(1)));
				shipRepository.save(new Ship("Patrol Boat", gamePlayerRepo.getOne(1)));
				//-------------- game 1 - turn 1 ---------------//
				salvoRepository.save(new Salvo(gamePlayerRepo.findById(1).get(), 1));

				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.findById(1).get(), "B5"));
				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(1), "C5"));
				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(1), "F1"));

				salvoRepository.save(new Salvo(gamePlayerRepo.getOne(2), 1));
				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(2), "B4"));
				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(2), "B5"));
				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(2), "B6"));
				//-------------game 1 -turn 2 ------------//
				salvoRepository.save(new Salvo(gamePlayerRepo.findById(1).get(), 2));

				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(3), "F2"));
				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(3), "D5"));

				salvoRepository.save(new Salvo(gamePlayerRepo.getOne(2), 2));
				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(4), "E1"));
				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(4), "H3"));
				salvoLocationsRepository.save(new SalvoLocation(salvoRepository.getOne(4), "A2"));
				scoreRepository.save(new Score(game1, jack, 1.5, LocalDateTime.now()));
				scoreRepository.save(new Score(game1, chloe, 0.0, LocalDateTime.now()));
				//scoreRepository.save(new Score(game2,jack,1.0,LocalDateTime.now()));
				//scoreRepository.save(new Score(game2,chloe,1.0,LocalDateTime.now()));


			};

		}

	}
/*
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	private UserDetailsService userDetailsService;



	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(inputName -> {
            System.out.println("inputname:"+inputName.toString());
			Player player = playerRepository.findByEmailAddress(inputName);

			if (player != null) {
				System.out.println(player);
				return new User(player.getEmailAddress(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				System.out.println("else");
				throw new UsernameNotFoundException("Unknown user:" + inputName);
			}
		});
		System.out.println(auth.getDefaultUserDetailsService());

	}
}*/
/*
@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService,PasswordEncoder passwordEncoder){
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
	protected void configure(HttpSecurity http) throws Exception {
        // turn off checking for CSRF tokens
        http.csrf().disable();

        http.cors().disable();


		http.authorizeRequests()
			//	.antMatchers(HttpMethod.POST, "/app/login").permitAll()
                .antMatchers(HttpMethod.GET,"/api/*").permitAll()//por el momento, no me permite aun logueado
				.antMatchers(HttpMethod.GET,"/web/**").permitAll()
				.antMatchers(HttpMethod.POST,"/api/players").permitAll()
                .anyRequest().authenticated()
				.and()
				.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/app/login").permitAll()
                .and()
                .logout().logoutUrl("/app/logout");




	// if user is not authenticated, just send an authentication failure response
  http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

	// if login is successful, just clear the flags asking for authentication
  http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

	// if login fails, just send an authentication failure response
  http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

	// if logout is successful, just send a success response
  http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());


}
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

	}
}

*/