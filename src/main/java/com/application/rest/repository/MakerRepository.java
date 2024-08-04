package com.application.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.application.rest.entities.Maker;

@Repository
public interface MakerRepository extends CrudRepository<Maker, Long> {
    
}
