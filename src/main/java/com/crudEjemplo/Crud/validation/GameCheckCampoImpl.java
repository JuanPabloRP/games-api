package com.crudEjemplo.Crud.validation;

import com.crudEjemplo.Crud.error.GameBadRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameCheckCampoImpl implements GameCheckCampoBuilder {
    List<String> mensajesError = new ArrayList<>();
    String mensajeError = "";

    //checkCampo(game.getNombre(), "Error, el nombre no puede estar vacío", (j) -> !j.isEmpty(), mensajesError);
//    if (Optional.ofNullable(campo).isPresent()) {
//        if(!condicion.test(campo)){
//            mensajesError.add(mensajeError);
//        }
//    }
    @Override
    public GameCheckCampoBuilder checkNombre(String nombre) {
        mensajeError = "Error, el nombre no puede estar vacío";
        if(Optional.ofNullable(nombre).isPresent() && nombre.isEmpty()){
            mensajesError.add(mensajeError);
        }
        return this;
    }

    @Override
    public GameCheckCampoBuilder checkDescripcion(String descripcion) {
        mensajeError = "Error, la descripción debe de tener mínimo 5 carácteres";
        if(Optional.ofNullable(descripcion).isPresent() && descripcion.length() < 5){
            mensajesError.add(mensajeError);
        }
        return this;
    }

    @Override
    public GameCheckCampoBuilder checkActivePlayers(Integer activePlayers) {
        mensajeError = "Error, la cantidad de jugadores activos no puede ser menor a 0";
        if(Optional.ofNullable(activePlayers).isPresent() && activePlayers < 0){
            mensajesError.add(mensajeError);
        }
        return this;
    }

    @Override
    public void throwSiEsInvalido() throws GameBadRequest {
        if(!mensajesError.isEmpty()){
            throw new GameBadRequest(String.join("\n", mensajesError));
        }
    }
}
