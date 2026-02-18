package com.superherogame.superhero_backend.controllers;

import com.superherogame.superhero_backend.repositories.UserRepository;
import com.superherogame.superhero_backend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/status")
    public String checkStatus() {
        System.out.println("Request received");
        return "ok";
    }

    @GetMapping("/api/warmup")
    public ResponseEntity<String> warmUp() {
        userRepository.count();
        return ResponseEntity.ok("DB is awake");
    }
}
