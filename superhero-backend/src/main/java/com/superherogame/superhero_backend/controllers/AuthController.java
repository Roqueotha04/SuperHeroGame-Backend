package com.superherogame.superhero_backend.controllers;

import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRegisterDTO userRegisterDto){
        return ResponseEntity.ok(authService.register(userRegisterDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserLoginDTO userLoginDTO){
        String token = authService.login(userLoginDTO);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
