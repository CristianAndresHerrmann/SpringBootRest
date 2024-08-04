package com.application.rest.persistence;

import java.util.List;
import java.util.Optional;

import com.application.rest.entities.Maker;

public interface MakerDAO {
    
    List<Maker> findAll();

    Optional<Maker> findById(Long id);

    void save(Maker maker);

    void deleteById(Long id);
    
}
