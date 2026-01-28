package com.superherogame.superhero_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(@NotBlank String nombre, @NotBlank String apellido, @Email String email, @NotBlank String password) {
}
