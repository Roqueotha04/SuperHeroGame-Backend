package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.Utils.JwtUtils;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional (readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, EmailService emailService, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFound("Usuario no encontrado con email: " + email));
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse findUserById(Long id) {
        return userMapper.toResponse(getUserOrThrow(id));
    }


    @Override
    @Transactional
    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }

    @Override
    @Transactional
    public UserResponse addHeroToFavoritesList(Long userId, Long HeroId) {
        AppUser appUser = getUserOrThrow(userId);
        if (appUser.getFavoritos().contains(HeroId)) throw new IllegalStateException("Heroe duplicado");

        appUser.getFavoritos().add(HeroId);
        return userMapper.toResponse(userRepository.save(appUser));
    }

    @Override
    @Transactional
    public UserResponse RemoveHeroFromFavoriteList(Long userId, Long HeroId) {
        AppUser appUser = getUserOrThrow(userId);
        if (!appUser.getFavoritos().contains(HeroId)) throw new IllegalStateException("El heroe no esta en la lista de favoritos");
        appUser.getFavoritos().remove(HeroId);
        return userMapper.toResponse(userRepository.save(appUser));
    }

    @Override
    @Transactional
    public UserResponse patchEmail(Long id, String email) {
        AppUser appUser = getUserOrThrow(id);
        if (appUser.getEmail().equals(email))throw new IllegalArgumentException("El nuevo email no puede ser igual al anterior");
        Optional<AppUser> auxUser= userRepository.findByEmail(email);
        if (auxUser.isPresent() && !auxUser.get().getId().equals(appUser.getId()))throw new IllegalStateException("El email ya esta en uso");
        appUser.setEmail(email);
        String token = jwtUtils.generateToken(appUser);
        emailService.sendConfirmationEmail(email, token);
        return userMapper.toResponse(userRepository.save(appUser));
    }

    @Override
    @Transactional
    public UserResponse patchPassword(Long id, PasswordUpdateRequest passwordUpdateRequest) {
        AppUser appUser = getUserOrThrow(id);
        if (passwordUpdateRequest.actualPassword().equals(passwordUpdateRequest.newPassword()))
            throw  new IllegalArgumentException("La nueva contraseña no puede ser igual a la anterior");
        if (!passwordEncoder.matches(passwordUpdateRequest.actualPassword(), appUser.getPassword())) throw new BadCredentialsException("La contraseña actual es incorrecta");
        appUser.setPassword(passwordEncoder.encode(passwordUpdateRequest.newPassword()));
        return userMapper.toResponse(userRepository.save(appUser));
    }

    @Override
    @Transactional
    public void resetPassword(Long id, String password) {
        AppUser user = getUserOrThrow(id);
        System.out.println("Antes: " + user.getPassword());
        user.setPassword(passwordEncoder.encode(password));
        System.out.println("Después: " + user.getPassword());
        userRepository.save(user);
        //userRepository.flush();
    }

    @Override
    @Transactional
    public void patchConfirmed(Long id, Boolean confirmed) {
        AppUser appUser = getUserOrThrow(id);
        if (appUser.isConfirmed()) throw new IllegalStateException("El usuario ya está confirmado");
        appUser.setConfirmed(true);
        userRepository.save(appUser);
    }

    @Override
    public AppUser getUserOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFound("Usuario no encontrado con id: " + id));
    }


}
