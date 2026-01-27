package com.superherogame.superhero_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    List<Long> idHeroes = new ArrayList<>();
}
