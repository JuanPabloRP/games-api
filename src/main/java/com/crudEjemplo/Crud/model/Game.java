package com.crudEjemplo.Crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(unique = true)
    @NotBlank(message = "Ingrese el nombre")
    private String nombre;
    @Setter
    @Length(min = 5)
    @NotBlank(message = "Ingrese la descripcion")
    private String descripcion;
    @Setter
    @NotNull(message = "Ingrese la cantidad de jugadores activos")
    @Min(0)
    private Integer activePlayers;

    public Game(Long id) {
        this.id = id;
    }
    public Game(String nombre, String descripcion, Integer activePlayers){
        this.id = this.getId();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activePlayers = activePlayers;
    }
    public String toString(){
        return String.format("ID: %s\nNombre: %s\nDescripcion: %s\nActive players: %s", this.id, this.nombre, this.descripcion, this.activePlayers);
    }
}
