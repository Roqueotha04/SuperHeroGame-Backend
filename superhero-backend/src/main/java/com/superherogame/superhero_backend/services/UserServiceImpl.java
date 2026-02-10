package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.PasswordUpdateRequest;
import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.exceptions.ResourceNotFound;
import com.superherogame.superhero_backend.mappers.UserAuthMapper;
import com.superherogame.superhero_backend.mappers.UserMapper;
import com.superherogame.superhero_backend.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFound("Could not found user with email: " + email));
    }

    @Override
    public UserResponse findUserById(Long id) {
        return userMapper.toResponse(getUserOrThrow(id));
    }


    @Override
    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }

    @Override
    public UserResponse addHeroToFavoritesList(Long userId, Long HeroId) {
        AppUser appUser = getUserOrThrow(userId);
        if (appUser.getFavoritos().contains(HeroId)) throw new IllegalStateException("Duplicated hero");

        appUser.getFavoritos().add(HeroId);
        return userMapper.toResponse(userRepository.save(appUser));
    }

    @Override
    public UserResponse RemoveHeroFromFavoriteList(Long userId, Long HeroId) {
        AppUser appUser = getUserOrThrow(userId);
        if (!appUser.getFavoritos().contains(HeroId)) throw new IllegalStateException("The hero is not in Favorites List");
        appUser.getFavoritos().remove(HeroId);
        return userMapper.toResponse(userRepository.save(appUser));
    }

    @Override
    public UserResponse patchEmail(Long id, String email) {
        AppUser appUser = getUserOrThrow(id);
        if (appUser.getEmail().equals(email))throw new IllegalArgumentException("new email cannot be the same as actual email");
        Optional<AppUser> auxUser= userRepository.findByEmail(email);
        if (auxUser.isPresent() && !auxUser.get().getId().equals(appUser.getId()))throw new IllegalStateException("Email already in use");
        appUser.setEmail(email);
        return userMapper.toResponse(userRepository.save(appUser));
    }

    @Override
    public UserResponse patchPassword(Long id, PasswordUpdateRequest passwordUpdateRequest) {
        AppUser appUser = getUserOrThrow(id);
        if (passwordUpdateRequest.actualPassword().equals(passwordUpdateRequest.newPassword()))
            throw  new IllegalArgumentException("New password cannot be the same as current password");
        if (!passwordEncoder.matches(passwordUpdateRequest.actualPassword(), appUser.getPassword())) throw new BadCredentialsException("Current password is incorrect");
        appUser.setPassword(passwordEncoder.encode(passwordUpdateRequest.newPassword()));
        return userMapper.toResponse(userRepository.save(appUser));
    }

    @Override
    public AppUser getUserOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFound("Could not found User with id: " + id));
    }


}
