package com.superherogame.superhero_backend.mappers;

import com.superherogame.superhero_backend.dto.PeleaRequest;
import com.superherogame.superhero_backend.dto.PeleaResponse;
import com.superherogame.superhero_backend.entities.Pelea;
import java.time.LocalDateTime;

public class PeleaMapper {

    public Pelea toPelea(PeleaRequest peleaRequest){
        return new Pelea(peleaRequest.idHeroe1(), peleaRequest.idHeroe2(), peleaRequest.idGanador(), LocalDateTime.now());
    }

    public PeleaResponse toResponse(Pelea pelea){
        return new PeleaResponse(pelea.getIdHeroe1(), pelea.getIdHeroe2(), pelea.getIdGanador(), pelea.getFechaPelea());
    }
}
