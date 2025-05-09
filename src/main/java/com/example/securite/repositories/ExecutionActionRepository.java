package com.example.securite.repositories;

import com.example.securite.entities.ExecutionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExecutionActionRepository extends JpaRepository<ExecutionAction, Long> {
    Optional<ExecutionAction> findByActionId(Long actionId);
}
