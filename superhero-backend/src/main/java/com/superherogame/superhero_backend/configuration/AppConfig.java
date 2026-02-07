package com.superherogame.superhero_backend.configuration;

import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    private final UserRepository userRepository;

    public AppConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            final User user = userRepository.findByEmail(username)
                    .orElseThrow() -> new
        }
    }*/
}
