package com.superherogame.superhero_backend.mappers;

import com.superherogame.superhero_backend.dto.PeleaResponse;
import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.entities.AppUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAuthMapper {

    private final PeleaMapper peleaMapper;

    public UserAuthMapper(PeleaMapper peleaMapper) {
        this.peleaMapper = peleaMapper;
    }

    public UserAuthResponse toResponse(AppUser appUser, String token){
        List<PeleaResponse> peleaResponseList = appUser.getHistorial().stream()
                .map(peleaMapper::toResponse)
                .toList();
        return new UserAuthResponse(appUser.getId(), appUser.getNombre(), appUser.getFavoritos(), appUser.getEquipo(), peleaResponseList, token);
    }
    public AppUser toAppUser(UserRegisterDTO userRegisterDTO){
        return new AppUser(userRegisterDTO.nombre(), userRegisterDTO.apellido(), userRegisterDTO.email(), userRegisterDTO.password());
    }

}
