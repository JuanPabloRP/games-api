package com.crudEjemplo.Crud.repository;

import com.crudEjemplo.Crud.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepositoryInterface extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    Boolean existsByNombre(String nombre);

}