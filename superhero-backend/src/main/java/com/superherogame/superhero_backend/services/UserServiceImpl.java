package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.entities.User;
import com.superherogame.superhero_backend.repositories.UserRepository;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
