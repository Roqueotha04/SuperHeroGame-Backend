package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.entities.User;

public interface UserService {
    public User findUserByEmail(String email);
}
