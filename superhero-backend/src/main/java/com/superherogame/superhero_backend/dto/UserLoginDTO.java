package com.superherogame.superhero_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(@Email String email, @NotBlank String password) {
}
