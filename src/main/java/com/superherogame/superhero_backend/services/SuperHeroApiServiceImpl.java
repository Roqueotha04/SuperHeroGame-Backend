package com.superherogame.superhero_backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class SuperHeroApiServiceImpl implements SuperHeroApiService{

    private final RestTemplate restTemplate= new RestTemplate();
    private static final String BASE_URL = "https://superheroapi.com/api.php/75b621e6e0413eee85b4f7383e2492c0";

    @Override
    public boolean existsHeroById(Long id) {
        try{
            restTemplate.getForObject(BASE_URL + id, Object.class);
            return true;
        }catch (HttpClientErrorException.NotFound e){
            return false;
        }
    }
}
