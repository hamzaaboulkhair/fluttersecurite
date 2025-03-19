package com.example.securite.repositories;

import com.example.securite.entities.Utilisateur;
import com.example.securite.entities.VisiteSecurite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisiteSecuriteRepository extends JpaRepository<VisiteSecurite, Long> {
    List<VisiteSecurite> findByDate(LocalDate date);
    List<VisiteSecurite> findByEtat(String etat);

}
