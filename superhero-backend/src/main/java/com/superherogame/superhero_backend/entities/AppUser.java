package com.superherogame.superhero_backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class AppUser {

    public AppUser(String nombre, String apellido, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.confirmed=true;
    }

    public AppUser() {
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
    @ElementCollection
    private List<Long> equipo=new ArrayList<>();
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Pelea> historial=new ArrayList<>();
}
