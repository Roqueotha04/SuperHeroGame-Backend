package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.entities.AppUser;

import java.util.List;

public interface UserService {
    public UserAuthResponse findUserByEmail(String email);
    public UserAuthResponse saveUser(AppUser appUser);
    public UserAuthResponse addHeroeToFavoriteList(Long userId, Long HeroeId);
    public UserAuthResponse RemoveHeroeFromFavoriteList(Long userId, Long HeroeId);
    public List<Long> getFavoriteList(Long userId);
}
