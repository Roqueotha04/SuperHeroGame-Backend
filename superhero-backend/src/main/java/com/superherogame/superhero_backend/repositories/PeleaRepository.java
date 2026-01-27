package com.superherogame.superhero_backend.repositories;

import com.superherogame.superhero_backend.entities.Pelea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeleaRepository extends JpaRepository<Pelea, Long> {}
