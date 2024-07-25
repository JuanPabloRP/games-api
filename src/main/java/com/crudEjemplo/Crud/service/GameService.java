package com.crudEjemplo.Crud.service;

import com.crudEjemplo.Crud.error.GameBadRequest;
import com.crudEjemplo.Crud.error.GameNotFoundException;
import com.crudEjemplo.Crud.model.Game;
import com.crudEjemplo.Crud.repository.GameRepositoryInterface;
import com.crudEjemplo.Crud.validation.GameCheckCampoImpl;
import com.crudEjemplo.Crud.validation.GameUpdateCampoImpl;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class GameService implements GameServiceInterface {

    private final GameRepositoryInterface gameRepositoryInterface;

    @Override
    public Game create(@NotNull Game game) throws GameBadRequest {
        return Optional.ofNullable(game)
                .filter((j)->!gameRepositoryInterface.existsByNombre(j.getNombre()))
                .map(gameRepositoryInterface::save)
                .orElseThrow(()-> new GameBadRequest("Error, ya existe un game con ese nombre"));
    }

    @Override
    public List<Game> findAll(Specification<Game> spec){
        return gameRepositoryInterface
                .findAll(spec);
    }

    @Override
    public Game findById(@NotNull Long id) throws GameNotFoundException {
        return gameRepositoryInterface.findById(id)
                .orElseThrow(()-> new GameNotFoundException("Error, el juego no se encontró"));
    }
    
    @Override
    public Optional<Game> update(@NotNull Long id, @NotNull Game game) throws GameBadRequest {
        return gameRepositoryInterface
                .findById(id)
                .map((juegoGuardado)->{
                    validacionesUpdate(game, juegoGuardado);
                    return Optional.of(gameRepositoryInterface.save(juegoGuardado));
                })
                .orElseThrow(()-> new GameBadRequest("Error, no se puede actualizar el game"));
    }

    @Override
    public Game delete(Long id) throws GameNotFoundException {
         return gameRepositoryInterface.findById(id)
                 .map(juego -> {
                     gameRepositoryInterface.deleteById(id);
                     return juego;
                 }).orElseThrow(()-> new GameNotFoundException("Error, no se pudo eliminar el juego"));
    }

    @Override
    public Boolean existsByNombre(String nombre){
       return gameRepositoryInterface.existsByNombre(nombre);
    }

    @Override
    public Boolean existsById(Long id){
        return gameRepositoryInterface.existsById(id);
    }

    // Métodos usados en update
    //---
    private static void validacionesUpdate(Game game, Game gameGuardado){
        try {
            checkGameToSave(game);
        } catch (GameBadRequest e) {
            throw new RuntimeException(e);
        }
        new GameUpdateCampoImpl(game, gameGuardado)
                .updateNombre()
                .updateDescripcion()
                .updateActivePlayers();

    }

    private static void checkGameToSave(@NotNull Game game) throws GameBadRequest {
        new GameCheckCampoImpl()
                .checkNombre(game.getNombre())
                .checkDescripcion(game.getDescripcion())
                .checkActivePlayers(game.getActivePlayers())
                .throwSiEsInvalido();
    }
}
