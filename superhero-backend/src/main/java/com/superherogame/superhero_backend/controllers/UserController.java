package com.superherogame.superhero_backend.controllers;

import com.superherogame.superhero_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http//localhost:4200")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getActualUser")
    public ResponseEntity<?>getActualUser(@RequestHeader ("access-token")String token){
        return userRepository.findByEmail(token)
                .map(user -> ResponseEntity.ok(Map.of("userResponse", user)))
                .orElse(ResponseEntity.status(404).build());
    }
}
