package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.Utils.JwtUtils;
import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.mappers.UserAuthMapper;
import com.superherogame.superhero_backend.repositories.UserRepository;
import lombok.Lombok;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final UserAuthMapper userAuthMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SuperHeroApiService superHeroApiService;
    private final UserRepository userRepository;

    public AuthServiceImpl(UserService userService, UserAuthMapper userAuthMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, SuperHeroApiService superHeroApiService, UserRepository userRepository) {
        this.userService = userService;
        this.userAuthMapper = userAuthMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.superHeroApiService = superHeroApiService;
        this.userRepository = userRepository;
    }

    @Override
    public UserAuthResponse register(UserRegisterDTO userRegisterDTO) {
        AppUser appUser=userAuthMapper.toAppUser(userRegisterDTO);
        Optional<AppUser> auxUser= userRepository.findByEmail(appUser.getEmail());
        if (auxUser.isPresent() && !auxUser.get().getId().equals(appUser.getId()))throw new IllegalStateException("El email ya esta en uso");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setEquipo(addHeroesToUser());
        appUser = userService.saveUser(appUser);

        String token = jwtUtils.generateToken(appUser);
        return userAuthMapper.toResponse(appUser, token);
    }

    @Override
    public List<Long> addHeroesToUser() {
        List<Long> heroesList = new ArrayList<>();
        Random rand = new Random();


        while (heroesList.size()<5){
            Long randomId = rand.nextLong(732) + 1;
           if (superHeroApiService.existsHeroById(randomId)){
               heroesList.add(randomId);
           }
        }
        return heroesList;
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
