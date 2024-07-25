package com.crudEjemplo.Crud;

import com.crudEjemplo.Crud.controller.GameRestController;
import com.crudEjemplo.Crud.model.Game;
import com.crudEjemplo.Crud.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = GameRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GameService gameService;
    private final String URL = "/api/v1/games";
    Game game;
    Long idgame;


    @BeforeEach
    public void setUp() {
        game = creategame();
        idgame = 1L;
    }


    // Create
    @Test
    public void testCreategameCorrectamente() throws Exception {
        given(gameService.create(game)).willReturn(game);

        ResultActions response = mockMvc.perform(post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(game)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // Get
    @Test
    public void testGetgames() throws Exception {
        List<Game> gamesList = new LinkedList<>();
        gamesList.add(game);
        Game game2 = creategame(2L, "Counter Strike 2", "Game de bala 2", 1000000);
        gamesList.add(game2);

        given(gameService.findAll(Specification.where(null))).willReturn(gamesList);

        MvcResult response = mockMvc.perform(get(URL)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andReturn();

        Assertions.assertEquals(200, response.getResponse().getStatus());
        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testGetgameById() throws Exception {
        given(gameService.findById(idgame)).willReturn(game);

        MvcResult response = mockMvc.perform(get(String.format(URL+"/%s", idgame))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        Assertions.assertEquals(200, response.getResponse().getStatus());
        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testDeletegame() throws Exception{
        given(gameService.delete(idgame)).willReturn(game);

        MvcResult response = mockMvc.perform(delete(String.format(URL+"/%s", idgame))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        Assertions.assertEquals(200, response.getResponse().getStatus());
        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    // TODO: hacer que funcione xd
    @Test
    public void testUpdategame() throws Exception{
//        Game gameActualizado = creategame(1L, "Counter Strike 2", "Game de bala", 100);
//
//        given(gameService.update(idgame, game))
//                .willReturn(Optional.of(gameActualizado));
//
//        MvcResult response = mockMvc.perform(put(String.format(URL+"/%s",idgame))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(game)))
//                .andReturn();
//
//        Assertions.assertEquals(200, response.getResponse().getStatus());
//        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
//        Assertions.assertNotEquals(game.getNombre(), response.getResponse().getContentAsString());
    }



    @Test
    @DisplayName("Prueba para verificar que el controlador este activo")
    public void testHealth() throws Exception {
        mockMvc.perform(get(URL+"/check"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello world"));
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

}
