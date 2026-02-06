package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.repositories.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public UserResponse addHeroeToFavoriteList(Long userId, Long HeroeId) {
        return null;
    }

    @Override
    public UserResponse RemoveHeroeFromFavoriteList(Long userId, Long HeroeId) {
        return null;
    }

    @Override
    public List<Long> getFavoriteList(Long userId) {
        return List.of();
    }
}
