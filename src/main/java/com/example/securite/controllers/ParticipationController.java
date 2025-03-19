package com.example.securite.controllers;

import com.example.securite.entities.Participation;
import com.example.securite.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {
    @Autowired
    private ParticipationService participationService;

    @GetMapping
    public List<Participation> getAllParticipations() {
        return participationService.getAllParticipations();
    }

    @GetMapping("/{id}")
    public Optional<Participation> getParticipationById(@PathVariable Long id) {
        return participationService.getParticipationById(id);
    }

    @GetMapping("/{visiteurId}")
    public List<Participation> getParticipationsByVisiteur(@PathVariable Long visiteurId) {
        return participationService.getParticipationsByVisiteur(visiteurId);
    }

    @GetMapping("/{apsId}")
    public List<Participation> getParticipationsByAPS(@PathVariable Long apsId) {
        return participationService.getParticipationsByAPS(apsId);
    }

    @GetMapping("/{responsableId}")
    public List<Participation> getParticipationsByResponsable(@PathVariable Long responsableId) {
        return participationService.getParticipationsByResponsable(responsableId);
    }

    @PostMapping
    public Participation createParticipation(@RequestBody Participation participation) {
        return participationService.createParticipation(participation);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipation(@PathVariable Long id) {
        participationService.deleteParticipation(id);
    }

    @GetMapping("/visite/{id}/participants")
    public ResponseEntity<List<Map<String, Object>>> getParticipants(@PathVariable Long id) {
        List<Map<String, Object>> participants = participationService.getParticipantsByVisite(id);

        return ResponseEntity.ok(participants);
    }

}
