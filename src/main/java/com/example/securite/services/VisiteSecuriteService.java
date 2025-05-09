package com.example.securite.services;

import com.example.securite.entities.*;
import com.example.securite.repositories.*;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisiteSecuriteService {

    private final VisiteSecuriteRepository visiteSecuriteRepository;
    private final ParticipationRepository participationRepository;
    private final VisiteurRepository visiteurRepository;
    private final APSRepository apsRepository;
    private final ResponsableRepository responsableRepository;
    private final ObjetVisiteRepository objetVisiteRepository;

    @Autowired
    public VisiteSecuriteService(
            VisiteSecuriteRepository visiteSecuriteRepository,
            ParticipationRepository participationRepository,
            VisiteurRepository visiteurRepository,
            APSRepository apsRepository,
            ResponsableRepository responsableRepository, ObjetVisiteRepository objetVisiteRepository) {
        this.visiteSecuriteRepository = visiteSecuriteRepository;
        this.participationRepository = participationRepository;
        this.visiteurRepository = visiteurRepository;
        this.apsRepository = apsRepository;
        this.responsableRepository = responsableRepository;
        this.objetVisiteRepository = objetVisiteRepository;
    }

    @Transactional
    public Optional<VisiteSecurite> getVisiteByIdWithParticipants(Long id) {
        return visiteSecuriteRepository.findById(id)
                .map(visite -> {
                    Hibernate.initialize(visite.getParticipants());
                    return visite;
                });
    }


    public List<VisiteSecurite> getAllVisites() {
        return visiteSecuriteRepository.findAll();
    }

    public Optional<VisiteSecurite> getVisiteById(Long id) {
        return visiteSecuriteRepository.findById(id);
    }

    public List<VisiteSecurite> getVisitesByDate(LocalDate date) {
        return visiteSecuriteRepository.findByDate(date);
    }

    public List<VisiteSecurite> getVisitesByEtat(String etat) {
        return visiteSecuriteRepository.findByEtat(etat);
    }

    @Transactional
    public VisiteSecurite createVisite(VisiteSecurite visite, Long utilisateurId, String role) {

        System.out.println("Utilisateur ID reçu : " + utilisateurId);
        System.out.println("Objet Visite ID reçu : " + visite.getObjetVisite());

        // ✅ Sauvegarde la visite
        VisiteSecurite savedVisite = visiteSecuriteRepository.save(visite);

        Participation participation = new Participation();
        participation.setVisite(savedVisite);

        switch (role) {
            case "VISITEUR":
                Visiteur visiteur = visiteurRepository.findById(utilisateurId)
                        .orElseThrow(() -> new RuntimeException("Visiteur non trouvé"));
                participation.setVisiteur(visiteur);
                break;

            case "APS":
                APS aps = apsRepository.findById(utilisateurId)
                        .orElseThrow(() -> new RuntimeException("APS non trouvé"));
                participation.setAps(aps);
                break;

            case "RESPONSABLE":
                Responsable responsable = responsableRepository.findById(utilisateurId)
                        .orElseThrow(() -> new RuntimeException("Responsable non trouvé"));
                participation.setResponsable(responsable);
                break;

            default:
                throw new IllegalArgumentException("Rôle inconnu : " + role);
        }

        // ✅ Sauvegarde la participation
        participationRepository.save(participation);

        return savedVisite;
    }

    public void deleteVisite(Long id) {
        visiteSecuriteRepository.deleteById(id);
    }

    public VisiteSecurite updateVisite(Long id, VisiteSecurite visite) {
        visiteSecuriteRepository.save(visite);
        return visite;
    }

    // ✅ Méthode pour récupérer les visites associées à un utilisateur
    public List<VisiteSecurite> getVisitesByUtilisateur(Utilisateur utilisateur) {
        if (utilisateur instanceof APS) {
            return participationRepository.findByAps((APS) utilisateur)
                    .stream().map(Participation::getVisite).collect(Collectors.toList());
        } else if (utilisateur instanceof Responsable) {
            return participationRepository.findByResponsable((Responsable) utilisateur)
                    .stream().map(Participation::getVisite).collect(Collectors.toList());
        } else if (utilisateur instanceof Visiteur) {
            return participationRepository.findByVisiteur((Visiteur) utilisateur)
                    .stream().map(Participation::getVisite).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Type d'utilisateur inconnu");
        }
    }

    @Transactional
    public List<VisiteSecurite> getVisitesByAps(Long apsId) {
        // Vérification que l'APS existe
        if (!apsRepository.existsById(apsId)) {
            throw new RuntimeException("APS non trouvé avec ID: " + apsId);
        }

        return visiteSecuriteRepository.findVisitesByApsIdWithDetails(apsId);
    }

    @Transactional
    public List<VisiteSecurite> getVisitesByResponsable(Long responsableId) {
        if (!responsableRepository.existsById(responsableId)) {
            throw new RuntimeException("Responsable non trouvé avec ID: " + responsableId);
        }

        return visiteSecuriteRepository.findVisitesByResponsableIdWithDetails(responsableId);
    }




}
