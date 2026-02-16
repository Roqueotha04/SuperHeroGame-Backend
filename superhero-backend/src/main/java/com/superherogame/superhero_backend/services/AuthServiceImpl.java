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

import java.util.*;

@Service
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

        String confirmationToken = UUID.randomUUID().toString();

        String link = "http://localhost:4200/confirmemail/" + confirmationToken;

        String message = buildConfirmationEmail(link);

        emailService.sendEmail(
                user.getEmail(),
                "SuperHeroGame - Email confirmation",
                message
        );
    }

    private String buildConfirmationEmail(String link) {
        return """
        <html>
            <body style="font-family: Arial, sans-serif;">
                <h2>Welcome to SuperHeroGame!</h2>
                <p>Please confirm your email by clicking the button below:</p>
                <a href="%s"
                   style="background-color:#1976d2;
                          color:white;
                          padding:10px 20px;
                          text-decoration:none;
                          border-radius:5px;">
                   Confirm Email
                </a>
                <p>This link will expire soon.</p>
            </body>
        </html>
    """.formatted(link);
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
