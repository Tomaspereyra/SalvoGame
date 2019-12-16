package com.codeoftheweb.salvo.repository;

import com.codeoftheweb.salvo.model.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GamePlayerRepository extends JpaRepository<GamePlayer,Integer> {
    @Query("SELECT g FROM GamePlayer g WHERE g.player.id = ?1 ")
    GamePlayer findGamePlayerByPlayerParam(Integer id);

}
