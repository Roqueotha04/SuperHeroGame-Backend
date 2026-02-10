package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;

import java.util.List;

public interface AuthService {
    public UserAuthResponse register(UserRegisterDTO userRegisterDTO);
    public List<Long> addHeroesToUser();
    public UserAuthResponse login (UserLoginDTO userLoginDTO);
}
