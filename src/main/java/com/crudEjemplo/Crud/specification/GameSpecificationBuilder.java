package com.crudEjemplo.Crud.specification;

import com.crudEjemplo.Crud.model.Game;
import org.springframework.data.jpa.domain.Specification;

public interface GameSpecificationBuilder {
    GameSpecificationBuilder buildNombre(String nombre);
    GameSpecificationBuilder buildDescripcion(String descripcion);
    GameSpecificationBuilder buildActivePlayers(Integer activePlayers);

    Specification<Game> build();
}
