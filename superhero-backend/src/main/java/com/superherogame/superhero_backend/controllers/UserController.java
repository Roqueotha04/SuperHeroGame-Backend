package com.superherogame.superhero_backend.controllers;

import com.superherogame.superhero_backend.configuration.service.CurrentUserService;
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

    private final CurrentUserService currentUserService;

    public UserController(UserService userService, CurrentUserService currentUserService) {
        this.userService = userService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/getActualUser")
    public AppUser getActualUser(){
       return userService.findUserById(currentUserService.getId());
    }
}
