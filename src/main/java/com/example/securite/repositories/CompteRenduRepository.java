package com.example.securite.repositories;

import com.example.securite.entities.CompteRendu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRenduRepository extends JpaRepository<CompteRendu, Long> {
    List<CompteRendu> findByStatut(String statut);
}