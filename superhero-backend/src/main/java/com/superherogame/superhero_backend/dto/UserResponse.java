package com.superherogame.superhero_backend.dto;

import com.superherogame.superhero_backend.entities.Pelea;

import java.util.List;

public record UserResponse(Long userId, String username, List<Long> listaFavoritos, List<Long>listaEquipo, List<PeleaResponse>historial) {
}
