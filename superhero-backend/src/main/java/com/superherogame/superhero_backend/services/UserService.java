package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;

import java.util.List;

public interface UserService {
    public AppUser findUserByEmail(String email);
    public AppUser findUserById(Long id);
    public AppUser saveUser(AppUser appUser);
    public UserResponse addHeroeToFavoritesList(Long userId, Long HeroeId);
    public UserResponse RemoveHeroeFromFavoriteList(Long userId, Long HeroeId);
    public UserResponse getFavoritesList(Long userId);
    public AppUser getUserOrThrow(Long id);
}
