package com.superherogame.superhero_backend.dto;

import com.superherogame.superhero_backend.entities.Pelea;

import java.util.List;

public record UserAuthResponse(Long userId, String username, List<Long>favoritos, List<Long>equipo, List<PeleaResponse>historial, String token) {
}
