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
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFound("Could not found user with email: " + email));
    }

    @Override
    public AppUser findUserById(Long id) {
        return getUserOrThrow(id);
    }


    @Override
    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }

    @Override
    public UserResponse addHeroeToFavoritesList(Long userId, Long HeroeId) {
        AppUser appUser = getUserOrThrow(userId);
        if (appUser.getFavoritos().contains(HeroeId)) throw new IllegalStateException("Duplicated heroe");

        appUser.getFavoritos().add(HeroeId);
        return userMapper.toResponse(userRepository.save(appUser));
    }

    @Override
    public UserResponse RemoveHeroeFromFavoriteList(Long userId, Long HeroeId) {
        return null;
    }

    @Override
    public UserResponse getFavoritesList (Long userId) {
        return userMapper.toResponse(getUserOrThrow(userId));
    }

    @Override
    public AppUser getUserOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFound("Could not found User with id: " + id));
    }


}
