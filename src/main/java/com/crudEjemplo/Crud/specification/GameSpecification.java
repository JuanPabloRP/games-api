package com.crudEjemplo.Crud.specification;

import com.crudEjemplo.Crud.model.Game;
import org.springframework.data.jpa.domain.Specification;

public class GameSpecification implements GameSpecificationInterface {

    public static Specification<Game> nombreContieneLetras(String nombre) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nombre"), "%" + nombre + "%"));
    }

    public static Specification<Game> descripcionContieneLetras(String descripcion) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("descripcion"), "%" + descripcion + "%"));
    }


    public static Specification<Game> jugadoresActivosMayoresOIgualA(Integer activePlayers){
        return (((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("activePlayers"),activePlayers)));
    }

}
