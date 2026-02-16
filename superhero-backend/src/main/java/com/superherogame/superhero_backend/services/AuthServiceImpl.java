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
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Confirmación de Email</title>
    </head>
    <body style="margin:0; padding:0; background-color:#f4f6f8; font-family: Arial, sans-serif;">
    
        <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f8; padding:40px 0;">
            <tr>
                <td align="center">
    
                    <table width="600" cellpadding="0" cellspacing="0" 
                           style="background-color:#ffffff; padding:40px; border-radius:8px;">
                        
                        <tr>
                            <td align="center" style="color:#1976d2; font-size:22px; font-weight:bold; padding-bottom:15px;">
                                ¡Qué bueno tenerte por aquí!
                            </td>
                        </tr>

                        <tr>
                            <td style="color:#333333; font-size:16px; padding-bottom:25px;">
                                Gracias por registrarte en <strong>SuperHeroGame</strong>.<br><br>
                                Solo falta un paso para comenzar tu aventura 🦸‍♂️🦸‍♂️.
                            </td>
                        </tr>

                        <tr>
                            <td align="center" style="padding-bottom:30px;">
                                <a href="%s"
                                   style="background-color:#1976d2;
                                          color:#ffffff;
                                          padding:14px 28px;
                                          text-decoration:none;
                                          border-radius:6px;
                                          font-size:16px;
                                          font-weight:bold;
                                          display:inline-block;">
                                   Confirmar mi email
                                </a>
                            </td>
                        </tr>

                        <tr>
                            <td style="color:#777777; font-size:13px; padding-bottom:30px;">
                                Este enlace expirará en unos minutos.<br>
                                Si no solicitaste esta cuenta, simplemente ignora este mensaje.
                            </td>
                        </tr>

                        <tr>
                            <td align="center" style="color:#999999; font-size:12px; padding-top:20px; border-top:1px solid #eeeeee;">
                                © SuperHeroGame — All Rights Reserved
                            </td>
                        </tr>

                    </table>

                </td>
            </tr>
        </table>

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
