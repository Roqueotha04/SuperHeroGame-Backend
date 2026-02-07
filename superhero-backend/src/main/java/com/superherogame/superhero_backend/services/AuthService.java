package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;

public interface AuthService {
    public UserAuthResponse register(UserRegisterDTO userRegisterDTO);
    public String login (UserLoginDTO userLoginDTO);
}
