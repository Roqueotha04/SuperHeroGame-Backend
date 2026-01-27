package com.superherogame.superhero_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String email;
    private String password;
    private List<Long> favoritos;

    private List<Equipo> equipos;

    private List<Pelea> historial;
}
