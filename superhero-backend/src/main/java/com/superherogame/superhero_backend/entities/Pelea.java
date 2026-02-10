package com.superherogame.superhero_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pelea {

    public Pelea(AppUser appUser, Long idHeroe1, Long idHeroe2, Long idGanador, LocalDateTime fechaPelea) {
        this.appUser = appUser;
        this.idHeroe1 = idHeroe1;
        this.idHeroe2 = idHeroe2;
        this.idGanador = idGanador;
        this.fechaPelea = fechaPelea;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    Long idHeroe1;
    Long idHeroe2;
    Long idGanador;
    LocalDateTime fechaPelea;
}
