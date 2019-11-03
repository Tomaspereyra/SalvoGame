package com.codeoftheweb.salvo.repository;

import com.codeoftheweb.salvo.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    @Override
    List<Player> findAll();

    Player findByUserName(@Param("username") String username);

    Player findByEmailAddress(String email);
}
