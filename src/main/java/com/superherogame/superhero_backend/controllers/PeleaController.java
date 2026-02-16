package com.superherogame.superhero_backend.controllers;

import com.superherogame.superhero_backend.configuration.service.CurrentUserService;
import com.superherogame.superhero_backend.dto.PeleaRequest;
import com.superherogame.superhero_backend.dto.PeleaResponse;
import com.superherogame.superhero_backend.services.PeleaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pelea")
public class PeleaController {

    private final CurrentUserService currentUserService;
    private final PeleaService peleaService;

    public PeleaController(CurrentUserService currentUserService, PeleaService peleaService) {
        this.currentUserService = currentUserService;
        this.peleaService = peleaService;
    }

    @GetMapping("/getPeleas")
    public List<PeleaResponse> getPeleasByUser(){
        return peleaService.getPeleasByUser(currentUserService.getId());
    }

    @PostMapping("/agregar")
    public PeleaResponse addPeleaToUser(@RequestBody PeleaRequest peleaRequest){
        return peleaService.addPeleaToUser(currentUserService.getId(), peleaRequest);
    }
}
