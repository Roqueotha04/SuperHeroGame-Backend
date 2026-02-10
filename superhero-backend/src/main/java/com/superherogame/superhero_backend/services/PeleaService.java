package com.superherogame.superhero_backend.services;

import com.superherogame.superhero_backend.dto.PeleaRequest;
import com.superherogame.superhero_backend.dto.PeleaResponse;

import java.util.List;

public interface PeleaService {
    public List<PeleaResponse> getPeleasByUser(Long id);
    public PeleaResponse addPeleaToUser(Long id, PeleaRequest peleaRequest);
}
