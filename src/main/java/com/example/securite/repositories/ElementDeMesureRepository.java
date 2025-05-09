package com.example.securite.repositories;

import com.example.securite.entities.AgentFunction;
import com.example.securite.entities.ElementDeMesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ElementDeMesureRepository extends JpaRepository<ElementDeMesure, Long> {
    List<ElementDeMesure> findByPlanActionId(Long planActionId);

}
