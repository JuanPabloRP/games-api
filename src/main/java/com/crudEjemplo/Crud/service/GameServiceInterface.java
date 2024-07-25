package com.crudEjemplo.Crud.service;

import com.crudEjemplo.Crud.error.GameBadRequest;
import com.crudEjemplo.Crud.error.GameNotFoundException;
import com.crudEjemplo.Crud.model.Game;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface GameServiceInterface {
    Game create(Game game) throws GameBadRequest;
    List<Game> findAll(Specification<Game> spec);
    Game findById(Long id) throws GameNotFoundException, GameBadRequest;
    Boolean existsById(Long id);
    Boolean existsByNombre(String Nombre);
    Optional<Game> update(Long id, Game game) throws GameNotFoundException, GameBadRequest;
    Game delete(Long id) throws GameNotFoundException, GameBadRequest;
}
