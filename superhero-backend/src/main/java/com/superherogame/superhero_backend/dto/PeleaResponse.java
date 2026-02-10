package com.superherogame.superhero_backend.dto;
import java.time.LocalDateTime;

public record PeleaResponse(Long idHeroe1, Long idHeroe2, Long idGanador, LocalDateTime fechaPelea) {
}
