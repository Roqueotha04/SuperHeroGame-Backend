package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.exceptions.ResourceNotFound;
import com.superherogame.superhero_backend.mappers.UserAuthMapper;
import com.superherogame.superhero_backend.mappers.UserMapper;
import com.superherogame.superhero_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFound("Could not found user with email: " + email));

    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
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
