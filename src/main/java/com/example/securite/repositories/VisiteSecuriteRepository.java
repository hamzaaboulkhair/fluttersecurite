package com.example.securite.repositories;

import com.example.securite.entities.ObjetVisite;
import com.example.securite.entities.Utilisateur;
import com.example.securite.entities.VisiteSecurite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisiteSecuriteRepository extends JpaRepository<VisiteSecurite, Long> {
    List<VisiteSecurite> findByDate(LocalDate date);
    List<VisiteSecurite> findByEtat(String etat);
    @Query("SELECT DISTINCT v FROM VisiteSecurite v JOIN FETCH v.objetVisite o JOIN FETCH o.aps WHERE o.aps.id = :apsId")
    List<VisiteSecurite> findVisitesByApsIdWithDetails(@Param("apsId") Long apsId);
    @Query("SELECT DISTINCT v FROM VisiteSecurite v JOIN FETCH v.objetVisite o JOIN FETCH o.responsable WHERE o.responsable.id = :responsableId")
    List<VisiteSecurite> findVisitesByResponsableIdWithDetails(@Param("responsableId") Long responsableId);


}
