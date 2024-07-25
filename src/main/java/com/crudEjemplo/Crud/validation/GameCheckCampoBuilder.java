package com.crudEjemplo.Crud.validation;

import com.crudEjemplo.Crud.error.GameBadRequest;

public interface GameCheckCampoBuilder {
    GameCheckCampoBuilder checkNombre(String nombre);
    GameCheckCampoBuilder checkDescripcion(String descripcion);
    GameCheckCampoBuilder checkActivePlayers(Integer activePlayers);
    void throwSiEsInvalido() throws GameBadRequest;
}
