package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUser register(UserRegisterDTO userRegisterDTO) {
        AppUser appUser = new AppUser();

        appUser.setEmail(userRegisterDTO.email());
        appUser.setNombre(userRegisterDTO.nombre());
        appUser.setApellido(userRegisterDTO.apellido());
        appUser.setPassword(userRegisterDTO.password());

        return userRepository.save(appUser);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        AppUser appUser = userRepository.findByEmail(userLoginDTO.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (!appUser.getPassword().equals(userLoginDTO.password())) throw new RuntimeException("Incorrect password");
        return appUser.getEmail();
    }
}
