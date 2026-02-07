package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.Utils.JwtUtils;
import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.mappers.UserAuthMapper;
import com.superherogame.superhero_backend.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final UserAuthMapper userAuthMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserService userService, UserAuthMapper userAuthMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userAuthMapper = userAuthMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserAuthResponse register(UserRegisterDTO userRegisterDTO) {
        AppUser appUser=userAuthMapper.toAppUser(userRegisterDTO);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser = userService.saveUser(appUser);

        String token = jwtUtils.generateToken(appUser);
        return userAuthMapper.toResponse(appUser, token);
    }

    @Override
    public UserAuthResponse login(UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.email(),
                        userLoginDTO.password()
                )
        );
        AppUser appUser = userService.findUserByEmail(userLoginDTO.email());
        String token = jwtUtils.generateToken(appUser);
        return userAuthMapper.toResponse(appUser, token);
    }
}
