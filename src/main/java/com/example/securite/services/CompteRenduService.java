package com.example.securite.services;
import com.example.securite.entities.CompteRendu;
import com.example.securite.entities.VisiteSecurite;
import com.example.securite.repositories.CompteRenduRepository;
import com.example.securite.repositories.VisiteSecuriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompteRenduService {

    @Autowired
    private CompteRenduRepository compteRenduRepository;

    @Autowired
    private VisiteSecuriteRepository visiteSecuriteRepository;

    public List<CompteRendu> getAllComptesRendus() {
        return compteRenduRepository.findAll();
    }

    public Optional<CompteRendu> getCompteRenduById(Long id) {
        return compteRenduRepository.findById(id);
    }

    public List<CompteRendu> getComptesRendusByStatut(String statut) {
        return compteRenduRepository.findByStatut(statut);
    }

    public CompteRendu createCompteRendu(CompteRendu compteRendu, Long visiteId) {
        // Fetch the VisiteSecurite by its ID
        VisiteSecurite visite = visiteSecuriteRepository.findById(visiteId)
                .orElseThrow(() -> new IllegalArgumentException("Visite not found for id " + visiteId));

        // Set the VisiteSecurite in the CompteRendu object
        compteRendu.setVisite(visite);

        // Save the CompteRendu entity in the database
        return compteRenduRepository.save(compteRendu);
    }

    public void deleteCompteRendu(Long id) {
        compteRenduRepository.deleteById(id);
    }
}
