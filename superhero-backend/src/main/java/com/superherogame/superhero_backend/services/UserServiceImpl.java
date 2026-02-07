package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.repositories.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserAuthResponse findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public UserAuthResponse addHeroeToFavoriteList(Long userId, Long HeroeId) {
        return null;
    }

    @Override
    public UserAuthResponse RemoveHeroeFromFavoriteList(Long userId, Long HeroeId) {
        return null;
    }

    @Override
    public List<Long> getFavoriteList(Long userId) {
        return List.of();
    }
}
