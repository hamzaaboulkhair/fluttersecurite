package com.example.securite.controllers;

import com.example.securite.entities.CompteRendu;
import com.example.securite.entities.PlanAction;
import com.example.securite.repositories.CompteRenduRepository;
import com.example.securite.repositories.PlanActionRepository;
import com.example.securite.services.CompteRenduService;
import com.example.securite.services.PlanActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans-action")
public class PlanActionController {

    @Autowired
    private PlanActionService planActionService;

    @Autowired
    private CompteRenduService compteRenduService;

    @Autowired
    private CompteRenduRepository compteRenduRepository;

    @Autowired
    private PlanActionRepository planActionRepository;


    @GetMapping
    public List<PlanAction> getAllPlansAction() {
        return planActionService.getAllPlansAction();
    }

    @GetMapping("/{id}")
    public Optional<PlanAction> getPlanActionById(@PathVariable Long id) {
        return planActionService.getPlanActionById(id);
    }

    @GetMapping("/compte-rendu/{compteRenduId}")
    public List<PlanAction> getPlansByCompteRendu(@PathVariable Long compteRenduId) {
        return planActionService.getPlansByCompteRendu(compteRenduId);
    }

    @PostMapping("/{ajouter}")
    public ResponseEntity<PlanAction> createPlanAction(
            @RequestParam("compte_rendu_id") Long compteRenduId,
            @RequestParam("observation") String observation,
            @RequestParam("type") String type,
            @RequestParam("correctionImmediate") String correctionImmediate,
            @RequestParam("action") String action,
            @RequestParam("responsable") String responsable) {  // Add the responsable parameter

        // Vérifier et loguer l'ID reçu
        System.out.println("compte_rendu_id reçu : " + compteRenduId);

        if (compteRenduId == null) {
            return ResponseEntity.badRequest().body(null); // Ajout de validation pour vérifier que l'ID n'est pas nul
        }

        // Continuer avec le traitement de l'ID
        CompteRendu compteRendu = compteRenduService.getCompteRenduById(compteRenduId).orElse(null);

        if (compteRendu == null) {
            // Loguer si aucun CompteRendu n'a été trouvé
            System.out.println("Aucun CompteRendu trouvé pour l'ID : " + compteRenduId);
            return ResponseEntity.badRequest().body(null); // Retourner une erreur si aucun CompteRendu n'est trouvé
        }

        // Créer l'action et l'enregistrer dans la base de données
        PlanAction planAction = new PlanAction();
        planAction.setDate(LocalDate.now());
        planAction.setAction(action);
        planAction.setType(type);
        planAction.setObservation(observation);
        planAction.setCorrectionImmediate(correctionImmediate);
        planAction.setResponsable(responsable);  // Set the responsable parameter
        planAction.setCompteRendu(compteRendu);  // Assigner le compteRendu récupéré

        // Sauvegarder l'action et renvoyer la réponse
        PlanAction savedPlanAction = planActionService.createPlanAction(planAction, compteRenduId);

        return ResponseEntity.ok(savedPlanAction);
    }











    @DeleteMapping("/{id}")
    public void deletePlanAction(@PathVariable Long id) {
        planActionService.deletePlanAction(id);
    }
}
