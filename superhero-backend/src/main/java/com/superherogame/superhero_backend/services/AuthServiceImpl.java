package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.Utils.JwtUtils;
import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.mappers.UserMapper;
import com.superherogame.superhero_backend.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserAuthResponse register(UserRegisterDTO userRegisterDTO) {
        AppUser appUser = new AppUser();

        appUser.setEmail(userRegisterDTO.email());
        appUser.setNombre(userRegisterDTO.nombre());
        appUser.setApellido(userRegisterDTO.apellido());
        appUser.setPassword(passwordEncoder.encode(userRegisterDTO.password()));

        appUser=userRepository.save(appUser);
        String token = jwtUtils.generateToken(appUser);
        return userMapper.toResponse(appUser, token);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.email(),
                        userLoginDTO.password()
                )
        );
        AppUser appUser = userRepository.findByEmail(userLoginDTO)
    }
}
