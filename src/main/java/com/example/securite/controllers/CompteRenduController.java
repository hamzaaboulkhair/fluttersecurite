package com.example.securite.controllers;

import com.example.securite.entities.CompteRendu;
import com.example.securite.services.CompteRenduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comptes-rendus")
public class CompteRenduController {

    @Autowired
    private CompteRenduService compteRenduService;
    @GetMapping
    public List<CompteRendu> getAllComptesRendus() {
        return compteRenduService.getAllComptesRendus();
    }
    @GetMapping("/id")
    public Optional<CompteRendu> getCompteRenduById(@PathVariable Long id){
        return compteRenduService.getCompteRenduById(id);
    }

    @GetMapping("/status")
    public List<CompteRendu> getCompteRenduByStatus(@RequestParam String status){
        return compteRenduService.getComptesRendusByStatut(status);
    }

    @PostMapping
    public  CompteRendu createCompteRendu(@RequestBody CompteRendu compteRendu){
        return compteRenduService.createCompteRendu(compteRendu);
    }

    @DeleteMapping("/id")
    public  void deleteCompteRenduById(@PathVariable Long id){
        compteRenduService.deleteCompteRendu(id);
    }


}
