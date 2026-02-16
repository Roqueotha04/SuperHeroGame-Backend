package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.PasswordUpdateRequest;
import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {
    public AppUser findUserByEmail(String email);
    public List<UserResponse> findAllUsers();
    public UserResponse findUserById(Long id);
    public AppUser saveUser(AppUser appUser);
    public UserResponse addHeroToFavoritesList(Long userId, Long HeroId);
    public UserResponse RemoveHeroFromFavoriteList(Long userId, Long HeroId);
    public UserResponse patchEmail(Long id, String email);
    public UserResponse patchPassword(Long id, PasswordUpdateRequest passwordUpdateRequest);
    public void patchConfirmed(Long id, Boolean confirmed);
    public AppUser getUserOrThrow(Long id);
}
