package com.superherogame.superhero_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api/status")
    public String checkStatus() {
        System.out.println("Request received");
        return "ok";
    }
}
