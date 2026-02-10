package com.superherogame.superhero_backend.repositories;

import com.superherogame.superhero_backend.entities.Pelea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeleaRepository extends JpaRepository<Pelea, Long> {
    public List<Pelea> findByAppUserId(Long id);
}
