package com.example.securite.controllers;

import com.example.securite.entities.APS;
import com.example.securite.entities.ObjetVisite;
import com.example.securite.entities.Responsable;
import com.example.securite.entities.Utilisateur;
import com.example.securite.repositories.UtilisateurRepository;
import com.example.securite.services.APSService;
import com.example.securite.services.ObjetVisiteService;
import com.example.securite.services.ResponsableService;
import com.example.securite.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/objets-visite")
public class ObjetVisiteController {

    @Autowired
    private ObjetVisiteService objetVisiteService;
    @Autowired
    private APSService apsService;
    @Autowired
    private ResponsableService responsableService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public List<ObjetVisite> getAllObjetsVisite(@AuthenticationPrincipal UserDetails userDetails){
        return objetVisiteService.getAllObjetsVisite();
    }
    @GetMapping("/id")
    public Optional<ObjetVisite> getObjetVisiteById(@PathVariable Long id){
        return objetVisiteService.getObjetVisiteById(id);
    }

    @GetMapping("/localisation")
    public List<ObjetVisite> getObjetVisiteByLocalisation(@PathVariable String localisation){
        return objetVisiteService.getObjetsVisiteByLocalisation(localisation);
    }

    @GetMapping("/natures-traveaux")
    public List<ObjetVisite> getObjetsVisiteByNatureTravaux(String natureTravaux){
        return objetVisiteService.getObjetsVisiteByNatureTravaux(natureTravaux);
    }

    @PostMapping
    public ObjetVisite createObjetVisite(@RequestBody ObjetVisite objetVisite){
        return objetVisiteService.createObjetVisite(objetVisite);
    }

    @DeleteMapping("/id")
    public void deleteObjetVisiteById(@PathVariable Long id){
        objetVisiteService.deleteObjetVisite(id);
    }



}
