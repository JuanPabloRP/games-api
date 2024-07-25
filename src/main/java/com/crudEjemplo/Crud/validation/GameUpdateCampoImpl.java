package com.crudEjemplo.Crud.validation;

import com.crudEjemplo.Crud.model.Game;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
public class GameUpdateCampoImpl implements GameUpdateCampoBuilder {
    // Optional.ofNullable(game.getNombre()).ifPresent(gameGuardado::setNombre);
    //        Optional.ofNullable(game.getDescripcion()).ifPresent(gameGuardado::setDescripcion);
    //        Optional.ofNullable(game.getActivePlayers()).ifPresent(gameGuardado::setActivePlayers);
    Game game;
    Game gameGuardado;

    public GameUpdateCampoImpl(Game game, Game gameGuardado) {
        this.game = game;
        this.gameGuardado = gameGuardado;
    }

    @Override
    public GameUpdateCampoBuilder updateNombre() {
        Optional.ofNullable(this.game.getNombre()).ifPresent(this.gameGuardado::setNombre);
        return this;
    }

    @Override
    public GameUpdateCampoBuilder updateDescripcion() {
        Optional.ofNullable(game.getDescripcion()).ifPresent(gameGuardado::setDescripcion);
        return this;
    }

    @Override
    public GameUpdateCampoBuilder updateActivePlayers() {
        Optional.ofNullable(game.getActivePlayers()).ifPresent(gameGuardado::setActivePlayers);
        return this;
    }


}
