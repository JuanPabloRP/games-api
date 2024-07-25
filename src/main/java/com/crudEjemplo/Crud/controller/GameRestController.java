package com.crudEjemplo.Crud.controller;

import com.crudEjemplo.Crud.error.GameBadRequest;
import com.crudEjemplo.Crud.error.GameNotFoundException;
import com.crudEjemplo.Crud.model.Game;
import com.crudEjemplo.Crud.service.GameService;
import com.crudEjemplo.Crud.specification.GameSpecificationImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GameRestController.GAMES_URI)
@ControllerAdvice
@RequiredArgsConstructor
public class GameRestController {
    public static final String GAMES_URI = "/api/v1/games";
    private final GameService gameService;

    @PostMapping()
    public ResponseEntity<Game> create(@Valid @RequestBody Game game) throws GameBadRequest {
        Game gameCreado = gameService.create(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameCreado);
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAll(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer activePlayers,
            @RequestParam(required = false) String descripcion)
        {
        Specification<Game> spec = new GameSpecificationImpl()
                .buildNombre(nombre)
                .buildDescripcion(descripcion)
                .buildActivePlayers(activePlayers)
                .build();

        List<Game> games = gameService.findAll(spec);

        return ResponseEntity.status(HttpStatus.OK).body(games);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Game> getById(@PathVariable Long id) throws GameNotFoundException, GameBadRequest {
        Game game = gameService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(game);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Game> update(@NotNull @Valid @PathVariable Long id, @RequestBody Game game) throws GameBadRequest, GameNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.update(id, game)
                .orElseThrow(()-> new GameNotFoundException("No se puede actualizar este game"))
        );
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Game> delete(@PathVariable Long id) throws GameNotFoundException, GameBadRequest {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.delete(id));
    }

    @GetMapping(value = "/nombres")
    public ResponseEntity<Boolean> existByNombre(@RequestParam String nombre)throws GameNotFoundException, GameBadRequest {
        Boolean exists = gameService.existsByNombre(nombre);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
    }

    @GetMapping(value="/check")
    public ResponseEntity<String> check(){
        return ResponseEntity.status(HttpStatus.OK).body("hello world");
    }
}