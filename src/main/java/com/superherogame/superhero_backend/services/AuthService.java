package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;

import java.util.List;

public interface AuthService {
    public UserAuthResponse register(UserRegisterDTO userRegisterDTO);
    public void confirmUser(String token);
    public void resendConfirmationEmail(String email);
    public void sendForgetPasswordEmail(String email);
    public void resetPassword(String password, String confirmPassword, String token);
    public List<Long> addHeroesToUser();
    public UserAuthResponse login (UserLoginDTO userLoginDTO);
}
