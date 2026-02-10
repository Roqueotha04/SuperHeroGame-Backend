package com.superherogame.superhero_backend.mappers;

import com.superherogame.superhero_backend.dto.PeleaResponse;
import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.UserResponse;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final PeleaMapper peleaMapper;

    public UserMapper(PeleaMapper peleaMapper) {
        this.peleaMapper = peleaMapper;
    }

    public UserResponse toResponse(AppUser appUser){
        List<PeleaResponse> peleaResponseList = appUser.getHistorial().stream()
                .map(peleaMapper::toResponse)
                .toList();
        return new UserResponse(appUser.getId(), appUser.getNombre(), appUser.getFavoritos(), appUser.getEquipo(), peleaResponseList);
    }

}
