package com.example.securite.repositories;

import com.example.securite.entities.ObjetVisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ObjetVisiteRepository extends JpaRepository<ObjetVisite, Long> {
    List<ObjetVisite> findByLocalisation(String localisation);
    List<ObjetVisite> findByNatureTravaux(String natureTravaux);
    List<ObjetVisite> findByApsId(Long apsId);
}
