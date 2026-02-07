package com.superherogame.superhero_backend.mappers;

import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(AppUser appUser){
        return new UserResponse(appUser.getId(), appUser.getNombre(), appUser.getFavoritos(), appUser.getEquipos(), appUser.getHistorial());
    }

}
