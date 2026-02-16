package com.superherogame.superhero_backend.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.superherogame.superhero_backend.Utils.JwtUtils;
import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.mappers.UserAuthMapper;
import com.superherogame.superhero_backend.repositories.UserRepository;
import lombok.Lombok;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final UserAuthMapper userAuthMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SuperHeroApiService superHeroApiService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public AuthServiceImpl(UserService userService, UserAuthMapper userAuthMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, SuperHeroApiService superHeroApiService, UserRepository userRepository, EmailService emailService) {
        this.userService = userService;
        this.userAuthMapper = userAuthMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.superHeroApiService = superHeroApiService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public UserAuthResponse register(UserRegisterDTO userRegisterDTO) {
        AppUser appUser=userAuthMapper.toAppUser(userRegisterDTO);
        Optional<AppUser> auxUser= userRepository.findByEmail(appUser.getEmail());
        if (auxUser.isPresent() && !auxUser.get().getId().equals(appUser.getId()))throw new IllegalStateException("El email ya esta en uso");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setEquipo(addHeroesToUser());
        appUser = userService.saveUser(appUser);

        String token = jwtUtils.generateToken(appUser);


        sendConfirmationEmail(appUser);
        return userAuthMapper.toResponse(appUser, token);
    }

    private void sendConfirmationEmail(AppUser user) {

        String confirmationToken = jwtUtils.generateEmailConfirmationToken(user);

        String link = "http://localhost:4200/confirmemail/" + confirmationToken;

        emailService.sendConfirmationEmail(user.getEmail(), link);
    }

    public void resendConfirmationEmail(String email) {
        AppUser user = userService.findUserByEmail(email);

        if (user.isConfirmed()) {
            throw new IllegalStateException("Usuario ya confirmado");
        }

        String token = jwtUtils.generateEmailConfirmationToken(user);
        String link = "http://localhost:4200/confirmemail/" + token;
        emailService.sendConfirmationEmail(user.getEmail(), link);
    }

    @Transactional
    public void confirmUser(String token){
       DecodedJWT decodedJWT = jwtUtils.verifyToken(token);
       String type = jwtUtils.extractClaim(decodedJWT, "type");
       if (!"email_confirmation".equals(type)) throw new IllegalStateException("Token inválido");

       Long id = Long.parseLong(jwtUtils.extractSubject(decodedJWT));
       userService.patchConfirmed(id, true);

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
