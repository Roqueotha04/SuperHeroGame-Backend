package com.superherogame.superhero_backend.configuration.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    public Long getId(){
        Authentication auth= SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof Long)) {
            throw new AccessDeniedException("User not authenticated");
        }

        return (Long) auth.getPrincipal();
    }
}
