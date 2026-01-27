package com.superherogame.superhero_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pelea {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    Long idHeroe1;
    Long idHeroe2;
    Long idGanador;
    LocalDateTime fecha;
}
