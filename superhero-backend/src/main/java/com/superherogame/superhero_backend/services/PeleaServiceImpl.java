package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.PeleaRequest;
import com.superherogame.superhero_backend.dto.PeleaResponse;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.entities.Pelea;
import com.superherogame.superhero_backend.mappers.PeleaMapper;
import com.superherogame.superhero_backend.repositories.PeleaRepository;
import com.superherogame.superhero_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeleaServiceImpl implements PeleaService{

    private final PeleaRepository peleaRepository;
    private final UserService userService;
    private final PeleaMapper peleaMapper;

    public PeleaServiceImpl(PeleaRepository peleaRepository, UserService userService, PeleaMapper peleaMapper) {
        this.peleaRepository = peleaRepository;
        this.userService = userService;
        this.peleaMapper = peleaMapper;
    }

    @Override
    public List<PeleaResponse> getPeleasByUser(Long id) {
        return peleaRepository.findByAppUserId(id).stream()
                .map(peleaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PeleaResponse addPeleaToUser(Long id, PeleaRequest peleaRequest) {
        AppUser currentUser= userService.getUserOrThrow(id);
        Pelea pelea = peleaMapper.toPelea(peleaRequest, currentUser);
        if (pelea.getIdHeroe1().equals(pelea.getIdGanador())){
            currentUser.getEquipo().add(pelea.getIdHeroe2());
        }else{
            currentUser.getEquipo().remove(pelea.getIdHeroe1());
        }
        return peleaMapper.toResponse(peleaRepository.save(pelea));
    }
}
