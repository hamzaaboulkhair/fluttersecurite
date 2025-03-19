package com.example.securite.repositories;

import com.example.securite.entities.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
    List<Visiteur> findByEntite(String entite);
}