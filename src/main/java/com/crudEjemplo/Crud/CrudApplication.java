package com.crudEjemplo.Crud;

import com.crudEjemplo.Crud.model.Game;
import com.crudEjemplo.Crud.repository.GameRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication implements CommandLineRunner {

	@Autowired
	GameRepositoryInterface gameRepositoryInterface;


	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Override
	public void run(String... args){
		gameRepositoryInterface.save(new Game("Counter Strike", "Game de disparos", 1100));
		gameRepositoryInterface.save(new Game("Counter Strike 2", "Game de disparos", 250000));
		gameRepositoryInterface.save(new Game("Minecraft", "Game de bloques", 7000));
		gameRepositoryInterface.save(new Game("COD Mobile", "Game PVP", 30));

		System.out.println("""
                ########################################
                La base de datos se ha llenado con datos
                ########################################
                """);
	}


}
