package com.superherogame.superhero_backend.controllers;

import com.superherogame.superhero_backend.configuration.service.CurrentUserService;
import com.superherogame.superhero_backend.dto.EmailRequest;
import com.superherogame.superhero_backend.dto.PasswordUpdateRequest;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final CurrentUserService currentUserService;

    public UserController(UserService userService, CurrentUserService currentUserService) {
        this.userService = userService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/getActualUser")
    public UserResponse getActualUser(){
       return userService.findUserById(currentUserService.getId());
    }

    @PatchMapping("/agregarFavorito/{idHeroe}")
    public UserResponse addHerotoFavoritesList(@PathVariable Long idHeroe){
        return userService.addHeroToFavoritesList(currentUserService.getId(), idHeroe);
    }

    @PatchMapping("/eliminarFavorito/{idHeroe}")
    public UserResponse removeHeroFromFavoritesList(@PathVariable Long idHeroe){
        return userService.RemoveHeroFromFavoriteList(currentUserService.getId(), idHeroe);
    }

    @PatchMapping("/updateEmail")
    public UserResponse patchEmail(@RequestBody EmailRequest email){
        return userService.patchEmail(currentUserService.getId(), email.email());
    }

    @PatchMapping("/updatePassword")
    public UserResponse patchPassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest){
        return userService.patchPassword(currentUserService.getId(), passwordUpdateRequest);
    }


}
