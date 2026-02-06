package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.User;

public interface AuthService {
    public User register(UserRegisterDTO userRegisterDTO);
    public String login (UserLoginDTO userLoginDTO);
}
