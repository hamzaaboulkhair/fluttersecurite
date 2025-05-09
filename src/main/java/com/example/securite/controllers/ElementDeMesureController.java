package com.example.securite.controllers;

import com.example.securite.entities.CompteRendu;
import com.example.securite.entities.ElementDeMesure;
import com.example.securite.entities.PlanAction;
import com.example.securite.entities.VisiteSecurite;
import com.example.securite.repositories.PlanActionRepository;
import com.example.securite.repositories.VisiteSecuriteRepository;
import com.example.securite.services.ElementDeMesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/element-mesure")
@CrossOrigin(origins = "*") // À ajuster selon tes besoins de sécurité CORS
public class ElementDeMesureController {

    @Autowired
    private ElementDeMesureService service;

    @Autowired
    private VisiteSecuriteRepository visiteSecuriteRepository;

    @Autowired
    private PlanActionRepository planActionRepository;



    // ✅ Ajouter un élément de mesure
    @PostMapping("/add/{planActionId}")
    public ResponseEntity<ElementDeMesure> addElement(
            @PathVariable Long planActionId,
            @RequestBody ElementDeMesure elementDeMesure
    ) {
        // Ajouter l’élément de mesure
        ElementDeMesure savedElement = service.addElementDeMesure(
                planActionId,
                elementDeMesure.getElementEfficacite(),
                elementDeMesure.getDetailsEfficacite(),
                elementDeMesure.getResultat(),
                elementDeMesure.getNote()
        );

        // Récupérer le PlanAction
        PlanAction planAction = planActionRepository.findById(planActionId)
                .orElseThrow(() -> new RuntimeException("Plan d'action introuvable"));

        // Récupérer le CompteRendu lié
        CompteRendu compteRendu = planAction.getCompteRendu();

        // Récupérer la VisiteSecurite liée
        VisiteSecurite visite = compteRendu.getVisite();

        // Mettre à jour l’état de la visite
        if (visite != null) {
            visite.setEtat("cloture");
            visiteSecuriteRepository.save(visite);
        }

        return ResponseEntity.ok(savedElement);
    }


    // ✅ Récupérer les éléments de mesure d’un plan d’action
    @GetMapping("/plan-action/{planActionId}")
    public List<ElementDeMesure> getElementsByPlanAction(@PathVariable Long planActionId) {
        return service.getByPlanAction(planActionId);
    }
}
