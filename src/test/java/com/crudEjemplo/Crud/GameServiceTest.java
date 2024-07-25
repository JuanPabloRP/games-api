package com.crudEjemplo.Crud;

import com.crudEjemplo.Crud.error.GameBadRequest;
import com.crudEjemplo.Crud.error.GameNotFoundException;
import com.crudEjemplo.Crud.model.Game;
import com.crudEjemplo.Crud.repository.GameRepositoryInterface;
import com.crudEjemplo.Crud.service.GameService;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    GameRepositoryInterface gameRepositoryInterface;

    @InjectMocks
    GameService gameService;

    Game game;
    Long idgame;


    @BeforeEach
    public void setUp() {
        game = creategame();
        idgame = 1L;
    }

    // CREATE
    @Test
    @DisplayName("Crear game")
    public void testCreategameCorrectamente() throws GameBadRequest {
        // Se mockea los datos
        when(gameRepositoryInterface.existsByNombre(game.getNombre())).thenReturn(false);
        when(gameRepositoryInterface.save(game)).thenReturn(game);

        // Para después comparar
        Game gameCreado = gameService.create(game);

        //Se verifica el resultado
        Assertions.assertNotNull(game);
        Assertions.assertEquals(game, gameCreado);
        Assertions.assertEquals(game.getNombre(), gameCreado.getNombre());
        Assertions.assertEquals(game.getDescripcion(), gameCreado.getDescripcion());
        Assertions.assertEquals(game.getActivePlayers(), gameCreado.getActivePlayers());

        //Se verifica las interaciones con los mosck
        verify(gameRepositoryInterface, times(1)).existsByNombre(game.getNombre());
        verify(gameRepositoryInterface, times(1)).save(game);
    }

    @Test
    @DisplayName("Lanzar excepcion al intentar crear game porque el nombre del game ya existe")
    public void testCreategameYaExistente() {
        when(gameRepositoryInterface.existsByNombre(game.getNombre())).thenReturn(true);

        GameBadRequest exception = Assertions.assertThrows(GameBadRequest.class, () -> gameService.create(game));

        Assertions.assertEquals("Error, ya existe un game con ese nombre", exception.getMessage());

        verify(gameRepositoryInterface, never()).save(game);
    }

    // GET
    @Test
    @DisplayName("Obtener games")
    public void testGetgames() {
        List<Game> games = new LinkedList<>();
        Game game1 = creategame();
        games.add(game1);
        Game game2 = creategame(2L, "Minecraft", "Un game de bloques", 25000000);
        games.add(game2);

        when(gameRepositoryInterface.findAll(Specification.where(null))).thenReturn(games);

        List<Game> gamesComparar = gameService.findAll(Specification.where(null));

        Assertions.assertNotNull(gamesComparar);
        Assertions.assertEquals(gamesComparar, games);

        verify(gameRepositoryInterface, times(1)).findAll(Specification.where(null));
    }

    // GET BY ID
    @Test
    @DisplayName("Obtener game por id")
    public void testGetgameByIdCorrectamente() throws GameNotFoundException, GameBadRequest {
        when(gameRepositoryInterface.findById(idgame)).thenReturn(Optional.of(game));

        Game gameComparar = gameService.findById(idgame);

        Assertions.assertNotNull(gameComparar);
        Assertions.assertEquals(game, gameComparar);
        Assertions.assertEquals(game.getNombre(), gameComparar.getNombre());
        Assertions.assertEquals(game.getDescripcion(), gameComparar.getDescripcion());
        Assertions.assertEquals(game.getActivePlayers(), gameComparar.getActivePlayers());

        verify(gameRepositoryInterface, times(1)).findById(idgame);
    }

    @Test
    @DisplayName("Lanzar error por no encontrar id de un game")
    public void testGetgameByIdIncorrectamente() {
        GameNotFoundException exception =
                Assertions.assertThrows(
                        GameNotFoundException.class,
                        () -> gameService.findById(2L),
                        "Error, el game no se encontró");

        Assertions.assertEquals("Error, el game no se encontró", exception.getMessage());
    }

    // DELETE
    @Test
    @DisplayName("Eliminar un game correctamente")
    public void testDeletegameCorrectamente() throws GameBadRequest, GameNotFoundException {
        when(gameRepositoryInterface.findById(idgame)).thenReturn(Optional.of(game));
        doNothing().when(gameRepositoryInterface).deleteById(idgame);

        Game gameEliminado = gameService.delete(idgame);

        Assertions.assertNotNull(gameEliminado);

        Assertions.assertEquals(game, gameEliminado);

        verify(gameRepositoryInterface, times(1)).findById(idgame);
        verify(gameRepositoryInterface).deleteById(idgame);
    }

    /*
    @Test
    @DisplayName("Lanzar error al eliminar un  game ")
    public void testDeletegameIncorrectamente() throws GameBadRequest, GameNotFoundException {
        when(gameRepositoryInterface.findById(idgame)).thenReturn(Optional.of(game));
        doNothing().when(gameRepositoryInterface).deleteById(idgame);

        Game gameEliminado = gameService.delete(idgame);
        GameNotFoundException gameNotFoundExceptionxception =
                Assertions.assertThrows(
                        GameNotFoundException.class,
                        () -> gameService.delete(idgame),
                        "Error, no se pudo eliminar el game");

        Assertions.assertNotNull(gameEliminado);
        Assertions.assertEquals(game, gameEliminado);
        Assertions.assertEquals("Error, no se pudo eliminar el game", gameNotFoundExceptionxception.getMessage());

        verify(gameRepositoryInterface, times(1)).findById(idgame);
        verify(gameRepositoryInterface).deleteById(idgame);
    }
     */
    @Test
    @DisplayName("Lanzar error al eliminar un  game por no enviar ID ")
    public void testDeletegameIncorrectamentePorNoEnviarId() throws GameBadRequest, GameNotFoundException {
        GameNotFoundException gameNotFoundExceptionxception =
                Assertions.assertThrows(
                        GameNotFoundException.class,
                        () -> gameService.delete(null),
                        "Error, no se encontró game con id: " + game.getId());

        Assertions.assertEquals("Error, no se encontró game con id: " + game.getId(), gameNotFoundExceptionxception.getMessage());
    }

    // UPDATE
    @Test
    @DisplayName("Actualizar correctamente un game")
    public void testUpdategameCorrectamente() throws GameBadRequest, GameNotFoundException {
        when(gameRepositoryInterface.existsById(idgame)).thenReturn(true);
        when(gameRepositoryInterface.findById(idgame)).thenReturn(Optional.of(game));
        when(gameRepositoryInterface.save(game)).thenReturn(game);

        Boolean existe = gameService.existsById(idgame);
        Game gameEncontrado = gameService.findById(idgame);
        Game gameCreado = gameService.create(game);

        Assertions.assertTrue(existe);
        Assertions.assertNotNull(gameEncontrado);
        Assertions.assertNotNull(gameCreado);
        Assertions.assertEquals(game, gameEncontrado);
        Assertions.assertEquals(game, gameCreado);

        verify(gameRepositoryInterface).existsById(idgame);
        verify(gameRepositoryInterface).findById(idgame);
        verify(gameRepositoryInterface).save(game);
    }

    @Test
    @DisplayName("Lanzar error cuando se quiere actualizar el nombre de un game existente")
    public void testUpdategameNombreRepetidoPerogameExiste() {
//        when(gameRepositoryInterface.existsById(idgame)).thenReturn(true);
//        when(gameRepositoryInterface.existsByNombre(game.getNombre())).thenReturn(true);

        GameBadRequest gameBadRequest = Assertions.assertThrows(
                GameBadRequest.class,
                () -> gameService.update(idgame, game),
                "Error, no se puede actualizar el game");

        Assertions.assertEquals("Error, no se puede actualizar el game", gameBadRequest.getMessage());
    }

    @Test
    @DisplayName("Lanzar error cuando se quiere actualizar campos de un camppo cuando no es permitido")
    public void testNoUpdategame() {
        GameBadRequest gameNotFoundException = Assertions.assertThrows(
                GameBadRequest.class,
                () -> gameService.update(idgame, game),
                "Error, no se puede actualizar el game");

        Assertions.assertEquals("Error, no se puede actualizar el game", gameNotFoundException.getMessage());

    }

    // Find by id
    @Test
    @DisplayName("Encuentra un game por ID")
    public void testEncuentragamePorId() throws GameBadRequest, GameNotFoundException {
        when(gameRepositoryInterface.findById(idgame)).thenReturn(Optional.of(game));

        Game gameEncontrado = gameService.findById(idgame);

        Assertions.assertNotNull(gameEncontrado);
        Assertions.assertEquals(game, gameEncontrado);

        verify(gameRepositoryInterface, times(1)).findById(idgame);
    }

    // Find by name
    @Test
    @DisplayName("Encontrar game por nombre")
    public void testEncontrargamePorNombre() throws GameBadRequest, GameNotFoundException {
        when(gameRepositoryInterface.existsByNombre(game.getNombre())).thenReturn(true);

        Boolean existe = gameService.existsByNombre(game.getNombre());

        Assertions.assertTrue(existe);

        verify(gameRepositoryInterface, times(1)).existsByNombre(game.getNombre());

    }

    @Test
    @DisplayName("No encontrar game por nombre")
    public void testNoEncontrargamePorNombre() throws GameBadRequest, GameNotFoundException {
        when(gameRepositoryInterface.existsByNombre(game.getNombre())).thenReturn(false);

        Boolean existe = gameService.existsByNombre(game.getNombre());

        Assertions.assertFalse(existe);

        verify(gameRepositoryInterface, times(1)).existsByNombre(game.getNombre());
    }

    // ##############################################
    public Game creategame(){
        Long id = idgame;
        game = new Game(id);
        game.setNombre("Counter Strike");
        game.setDescripcion("Game de disparos");
        game.setActivePlayers(1000000);

        return game;
    }

    public Game creategame(Long id, String nombre, String descripcion, Integer activePlayers){
        game = new Game(id);
        game.setNombre(nombre);
        game.setDescripcion(descripcion);
        game.setActivePlayers(activePlayers);

        return game;
    }
    // ##############################################

    @Test
    public void testCuandoSeIntenteGuardarUngameConElMismoNombreDeOtroDaError() throws GameBadRequest {
        when(gameRepositoryInterface.existsByNombre(game.getNombre())).thenReturn(true);

        GameBadRequest gameBadRequest = Assertions.assertThrows(
                GameBadRequest.class,
                ()->gameService.create(game),
                "Error, ya existe un game con ese nombre");

        Assertions.assertEquals("Error, ya existe un game con ese nombre", gameBadRequest.getMessage());
    };

}
