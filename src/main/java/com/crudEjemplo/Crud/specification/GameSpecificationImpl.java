package com.crudEjemplo.Crud.specification;

import com.crudEjemplo.Crud.model.Game;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class GameSpecificationImpl implements GameSpecificationBuilder {
    Specification<Game> spec = Specification.where(null);

    @Override
    public GameSpecificationBuilder buildNombre(String nombre) {
        if(Objects.nonNull(nombre)){
            spec = spec.and(GameSpecification.nombreContieneLetras(nombre));
        }
        return this;
    }

    @Override
    public GameSpecificationBuilder buildDescripcion(String descripcion) {
        if(Objects.nonNull(descripcion)){
            spec = spec.and(GameSpecification.descripcionContieneLetras(descripcion));
        }
        return this;
    }

    @Override
    public GameSpecificationBuilder buildActivePlayers(Integer activePlayers) {
        if(Objects.nonNull(activePlayers)){
            spec = spec.and(GameSpecification.jugadoresActivosMayoresOIgualA(activePlayers));
        }
        return this;
    }

    @Override
    public Specification<Game> build() {
        return spec;
    }
}
