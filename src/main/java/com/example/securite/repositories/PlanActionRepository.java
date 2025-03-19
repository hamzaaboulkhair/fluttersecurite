package com.example.securite.repositories;

import com.example.securite.entities.PlanAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanActionRepository extends JpaRepository<PlanAction, Long> {
    List<PlanAction> findByCompteRenduId(Long compteRenduId);
}