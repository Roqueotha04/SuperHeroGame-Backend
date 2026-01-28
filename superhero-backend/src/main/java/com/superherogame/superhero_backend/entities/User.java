package com.superherogame.superhero_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    public User(String nombre, String apellido, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.confirmed=true;
    }

    public User() {
        this.confirmed=true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String email;
    private String password;

    private boolean confirmed;

    @ElementCollection
    private List<Long> favoritos=new ArrayList<>();
    @OneToMany
    private List<Equipo> equipos=new ArrayList<>();
    @OneToMany
    private List<Pelea> historial=new ArrayList<>();
}
