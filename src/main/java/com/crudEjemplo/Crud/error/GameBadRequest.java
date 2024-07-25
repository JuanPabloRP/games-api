package com.crudEjemplo.Crud.error;

public class GameBadRequest extends Exception{
    public GameBadRequest(String message) {
        super(message);
    }
}
