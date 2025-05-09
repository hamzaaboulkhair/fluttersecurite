package com.example.securite.repositories;

import com.example.securite.entities.AgentFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentFunctionRepository extends JpaRepository<AgentFunction, Long> {
    List<AgentFunction> findByVisiteSecuriteId(Long visiteId); // Get agent functions by visit ID

    Optional<Object> findByFunctionName(String functionName);
}
