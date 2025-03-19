package com.example.securite.repositories;

import com.example.securite.entities.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation,Long> {
    List<Observation> findByType(String type);
}