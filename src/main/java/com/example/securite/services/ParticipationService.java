package com.example.securite.services;

import com.example.securite.entities.Participation;
import com.example.securite.repositories.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParticipationService {
    @Autowired
    private ParticipationRepository participationRepository;

    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    public Optional<Participation> getParticipationById(Long id) {
        return participationRepository.findById(id);
    }

    public List<Participation> getParticipationsByVisiteur(Long visiteurId) {
        return participationRepository.findByVisiteurId(visiteurId);
    }

    public List<Participation> getParticipationsByAPS(Long apsId) {
        return participationRepository.findByApsId(apsId);
    }

    public List<Participation> getParticipationsByResponsable(Long responsableId) {
        return participationRepository.findByResponsableId(responsableId);
    }

    public Participation createParticipation(Participation participation) {
        return participationRepository.save(participation);
    }

    public void deleteParticipation(Long id) {
        participationRepository.deleteById(id);
    }
    public List<Map<String, Object>> getParticipantsByVisite(Long visiteId) {
        List<Participation> participations = participationRepository.findByVisiteId(visiteId);

        List<Map<String, Object>> participants = new ArrayList<>();

        for (Participation participation : participations) {
            Map<String, Object> participantData = new HashMap<>();

            if (participation.getVisiteur() != null) {
                participantData.put("id", participation.getVisiteur().getId());
                participantData.put("nom", participation.getVisiteur().getNom());
                participantData.put("email", participation.getVisiteur().getEmail());
                participantData.put("telephone", participation.getVisiteur().getTelephone());
                participantData.put("type_utilisateur", "Visiteur");
            } else if (participation.getAps() != null) {
                participantData.put("id", participation.getAps().getId());
                participantData.put("nom", participation.getAps().getNom());
                participantData.put("email", participation.getAps().getEmail());
                participantData.put("telephone", participation.getAps().getTelephone());
                participantData.put("type_utilisateur", "APS");
            } else if (participation.getResponsable() != null) {
                participantData.put("id", participation.getResponsable().getId());
                participantData.put("nom", participation.getResponsable().getNom());
                participantData.put("email", participation.getResponsable().getEmail());
                participantData.put("telephone", participation.getResponsable().getTelephone());
                participantData.put("type_utilisateur", "Responsable");
            }

            if (!participantData.isEmpty()) {
                participants.add(participantData);
            }
        }

        return participants;
    }

}

