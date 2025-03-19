package com.example.securite.repositories;

import com.example.securite.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByVisiteurId(Long visiteurid);
    List<Participation> findByApsId(Long apsid);
    List<Participation> findByResponsableId(Long responsableid);

    List<Participation> findByAps(APS aps);           // ✅ Trouver les participations d'un APS
    List<Participation> findByResponsable(Responsable responsable); // ✅ Trouver les participations d'un Responsable
    List<Participation> findByVisiteur(Visiteur visiteur);

    @Query("SELECT p FROM Participation p WHERE p.visite.id = :visiteId")
    List<Participation> findByVisiteId(@Param("visiteId") Long visiteId);

}