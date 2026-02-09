package com.superherogame.superhero_backend.controllers;

import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.repositories.UserRepository;
import com.superherogame.superhero_backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http//localhost:4200")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/getActualUser")
    public AppUser getActualUser(Authentication authentication){
       return userService.findUserById((Long) authentication.getPrincipal());
    }

    @GetMapping
    public String helloWorld(){
        return "Hello world";
    }
}
