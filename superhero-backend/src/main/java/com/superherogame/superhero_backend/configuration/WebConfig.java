package com.superherogame.superhero_backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir todas las rutas
                .allowedOrigins("http://localhost:4200") // Permitir tu Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permitir el preflight
                .allowedHeaders("*") // Permitir todos los headers (incluyendo tu access-token)
                .allowCredentials(true);
    }
}
