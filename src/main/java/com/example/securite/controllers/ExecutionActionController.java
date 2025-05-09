package com.example.securite.controllers;

import com.example.securite.entities.*;
import com.example.securite.repositories.PlanActionRepository;
import com.example.securite.repositories.VisiteSecuriteRepository;
import com.example.securite.services.ExecutionActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/execution_action")
@CrossOrigin(origins = "*")
public class ExecutionActionController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private ExecutionActionService executionActionService;

    @Autowired
    private PlanActionRepository planActionRepository;

    @Autowired
    private VisiteSecuriteRepository visiteSecuriteRepository;

    @PostMapping("/execution/{actionId}")
    public ResponseEntity<ExecutionAction> ajouterExecutionAction(
            @PathVariable("actionId") Long actionId,
            @RequestParam("dateEcheance") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEcheance,
            @RequestParam("datePrevueRealisation") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePrevue,
            @RequestParam("dateReelleRealisation") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateReelle,
            @RequestParam("commentaire") String commentaire,
            @RequestParam("realisee") boolean realisee,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        List<String> filePaths = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                try {
                    Files.write(filePath, file.getBytes());
                    filePaths.add(filePath.toString());
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            }
        }

        ExecutionAction executionAction = new ExecutionAction();
        executionAction.setDateEcheance(dateEcheance);
        executionAction.setDatePrevueRealisation(datePrevue);
        executionAction.setDateReelleRealisation(dateReelle);
        executionAction.setCommentaire(commentaire);
        executionAction.setRealisee(realisee);

        ExecutionAction saved = executionActionService.createExecutionAction(
                executionAction, actionId, filePaths);

        PlanAction planAction = planActionRepository.findById(actionId)
                .orElseThrow(() -> new RuntimeException("Plan d'action introuvable"));

        CompteRendu compteRendu = planAction.getCompteRendu();

        // Récupérer la VisiteSecurite liée
        VisiteSecurite visite = compteRendu.getVisite();

        // Mettre à jour l’état de la visite
        if (visite != null) {
            visite.setEtat("realise"); // ou "Clôturé" si tu veux l'affichage avec accent
            visiteSecuriteRepository.save(visite);
        }

        return ResponseEntity.ok(saved);
    }

}
