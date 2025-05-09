package com.example.securite.controllers;

import com.example.securite.entities.CompteRendu;
import com.example.securite.entities.VisiteSecurite;
import com.example.securite.repositories.VisiteSecuriteRepository;
import com.example.securite.services.CompteRenduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/comptes-rendus")
public class CompteRenduController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private CompteRenduService compteRenduService;

    @Autowired
    private VisiteSecuriteRepository visiteSecuriteRepository;

    // Constructor to ensure the uploads directory exists
    public CompteRenduController() {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Error creating upload directory", e);
            }
        }
    }

    @GetMapping
    public List<CompteRendu> getAllComptesRendus() {
        return compteRenduService.getAllComptesRendus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteRendu> getCompteRenduById(@PathVariable Long id) {
        return compteRenduService.getCompteRenduById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status")
    public List<CompteRendu> getCompteRenduByStatus(@RequestParam String status){
        return compteRenduService.getComptesRendusByStatut(status);
    }

    @PostMapping("/ajouter")
    public ResponseEntity<CompteRendu> ajouterCompteRendu(
            @RequestParam("contenu") String contenu,
            @RequestParam("visiteId") Long visiteId,
            @RequestParam Map<String, String> reponses,
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

        // Create and set CompteRendu fields
        CompteRendu compteRendu = new CompteRendu();
        compteRendu.setDateSoumission(LocalDate.now());
        compteRendu.setContenu(contenu);
        compteRendu.setMedias(filePaths);
        compteRendu.setStatut("Envoyé");
        compteRendu.setReponses(reponses);

        // Fetch the VisiteSecurite and update the "etat" (status)
        VisiteSecurite visiteSecurite = visiteSecuriteRepository.findById(visiteId)
                .orElseThrow(() -> new RuntimeException("Visite not found"));

        // Update the status of the visite (etat)
        visiteSecurite.setEtat("envoye");  // Update to the appropriate status
        visiteSecuriteRepository.save(visiteSecurite);  // Save the updated visite

        // Associate the CompteRendu with the VisiteSecurite
        compteRendu.setVisite(visiteSecurite);

        // Save and return the created CompteRendu
        CompteRendu savedCompteRendu = compteRenduService.createCompteRendu(compteRendu, visiteId);

        return ResponseEntity.ok(savedCompteRendu);
    }


    @DeleteMapping("/{id}")
    public void deleteCompteRenduById(@PathVariable Long id){
        compteRenduService.deleteCompteRendu(id);
    }
}
