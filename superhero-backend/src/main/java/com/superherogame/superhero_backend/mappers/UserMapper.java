package com.superherogame.superhero_backend.mappers;

import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.entities.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserAuthResponse toResponse(AppUser appUser, String token){
        return new UserAuthResponse(appUser.getId(), appUser.getNombre(), appUser.getFavoritos(), appUser.getEquipos(), appUser.getHistorial(), token);
    }
}
