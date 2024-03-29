package com.codeoftheweb.salvo.repository;

import com.codeoftheweb.salvo.model.SalvoLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SalvoLocationsRepository extends JpaRepository<SalvoLocation,Integer> {
}
