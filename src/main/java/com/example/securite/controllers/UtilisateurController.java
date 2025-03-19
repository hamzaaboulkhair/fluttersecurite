package com.example.securite.controllers;

import com.example.securite.entities.APS;
import com.example.securite.entities.Responsable;
import com.example.securite.entities.Utilisateur;
import com.example.securite.entities.Visiteur;
import com.example.securite.services.APSService;
import com.example.securite.services.ResponsableService;
import com.example.securite.services.UtilisateurService;
import org.springframework.web.bind.annotation.*;
import com.example.securite.services.VisiteurService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private VisiteurService visiteurService;

    @Autowired
    private APSService apsService;

    @Autowired
    private ResponsableService responsableService;


    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public Optional<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id);
    }

    @GetMapping("/email/{email}")
    public Optional<Utilisateur> getUtilisateurByEmail(@PathVariable String email) {
        return utilisateurService.getUtilisateurByEmail(email);
    }


    @GetMapping("/visiteurs")
    public List<Visiteur> getAllVisiteurs() {
        return visiteurService.getAllVisiteurs();
    }

    @GetMapping("/APS")
    public List<APS> getAllAPS() {
        return apsService.getAllAPS();
    }

    @GetMapping("/APS/{id}")
    public Optional<APS> getAPSById(@PathVariable Long id) {
        return apsService.getAPSById(id);
    }

    @GetMapping("/visiteur/{id}")
    public Optional<Visiteur> getVisiteurById(@PathVariable Long id) {
        return visiteurService.getVisiteurById(id);
    }

    @GetMapping("/visiteur/{entite}")
    public List<Visiteur> getVisiteurByEntite(@PathVariable String entite) {
        return visiteurService.getVisiteurByEntite(entite);
    }

    @GetMapping("/responsable/{id}")
    public  Optional<Responsable> getAllResponsableById(@PathVariable Long id) {
        return responsableService.getResponsableById(id);
    }

    @GetMapping("/responsables")
    public List<Responsable> getAllResponsables() {
        return responsableService.getAllResponsables();
    }


    @PostMapping("/visiteur")
    public Visiteur createVisiteur(@RequestBody Visiteur visiteur) {
        return (Visiteur) utilisateurService.createUtilisateur(visiteur);
    }

    @PostMapping("/aps")
    public APS createAPS(@RequestBody APS aps) {
        return (APS) utilisateurService.createUtilisateur(aps);
    }

    @PostMapping("/responsable")
    public Responsable createResponsable(@RequestBody Responsable responsable) {
        return (Responsable) utilisateurService.createUtilisateur(responsable);
    }


    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
    }
}
