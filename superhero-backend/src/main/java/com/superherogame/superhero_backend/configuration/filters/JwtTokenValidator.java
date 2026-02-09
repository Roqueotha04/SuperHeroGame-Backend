package com.superherogame.superhero_backend.configuration.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NotBlank HttpServletRequest request,
                                    @NotBlank HttpServletResponse response,
                                    @NotBlank FilterChain filterChain) throws ServletException, IOException {

    }
}
