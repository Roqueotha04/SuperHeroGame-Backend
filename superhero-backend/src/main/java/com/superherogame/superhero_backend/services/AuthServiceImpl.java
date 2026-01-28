package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.UserLoginDTO;
import com.superherogame.superhero_backend.dto.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.User;
import com.superherogame.superhero_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(UserRegisterDTO userRegisterDTO) {
        User user = new User();

        user.setEmail(userRegisterDTO.email());
        user.setNombre(userRegisterDTO.nombre());
        user.setApellido(userRegisterDTO.apellido());
        user.setPassword(userRegisterDTO.password());

        return userRepository.save(user);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByEmail(userLoginDTO.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(userLoginDTO.password())) throw new RuntimeException("Incorrect password");
        return user.getEmail();
    }
}
