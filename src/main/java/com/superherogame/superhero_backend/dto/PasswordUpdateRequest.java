package com.superherogame.superhero_backend.dto;

public record PasswordUpdateRequest(String actualPassword, String newPassword) {
}
