package com.example.securite.services;

import com.example.securite.entities.Visiteur;
import com.example.securite.repositories.VisiteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisiteurService {
    @Autowired
    private VisiteurRepository visiteurRepository;

    public List<Visiteur> getAllVisiteurs() {
        return visiteurRepository.findAll();
    }

    public Optional<Visiteur> getVisiteurById(Long id) {
        return visiteurRepository.findById(id);
    }

    public List<Visiteur> getVisiteurByEntite(String entite) {
        return visiteurRepository.findByEntite(entite);
    }

    public Visiteur createVisiteur(Visiteur visiteur) {
        return visiteurRepository.save(visiteur);
    }

    public void deleteVisiteur(Long id) {
        visiteurRepository.deleteById(id);
    }

}
