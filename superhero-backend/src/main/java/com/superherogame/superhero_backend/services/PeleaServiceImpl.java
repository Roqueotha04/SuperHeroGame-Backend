package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.PeleaRequest;
import com.superherogame.superhero_backend.dto.PeleaResponse;
import com.superherogame.superhero_backend.entities.AppUser;
import com.superherogame.superhero_backend.entities.Pelea;
import com.superherogame.superhero_backend.mappers.PeleaMapper;
import com.superherogame.superhero_backend.repositories.PeleaRepository;
import com.superherogame.superhero_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeleaServiceImpl implements PeleaService{

    private final PeleaRepository peleaRepository;
    private final UserRepository userRepository;
    private final PeleaMapper peleaMapper;

    public PeleaServiceImpl(PeleaRepository peleaRepository, UserRepository userRepository, PeleaMapper peleaMapper) {
        this.peleaRepository = peleaRepository;
        this.userRepository = userRepository;
        this.peleaMapper = peleaMapper;
    }

    @Override
    public List<PeleaResponse> getPeleasByUser(Long id) {
        return peleaRepository.findByAppUserId(id).stream()
                .map(peleaMapper::toResponse)
                .toList();
    }

    @Override
    public PeleaResponse addPeleaToUser(Long id, PeleaRequest peleaRequest) {
        AppUser userRef= userRepository.getReferenceById(id);
        Pelea pelea = peleaMapper.toPelea(peleaRequest, userRef);
        return peleaMapper.toResponse(peleaRepository.save(pelea));
    }
}
