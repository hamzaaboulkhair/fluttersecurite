package com.example.securite.services;
import com.example.securite.entities.CompteRendu;
import com.example.securite.repositories.CompteRenduRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompteRenduService {

    @Autowired
    private CompteRenduRepository compteRenduRepository;

    public List<CompteRendu> getAllComptesRendus() {
        return compteRenduRepository.findAll();
    }

    public Optional<CompteRendu> getCompteRenduById(Long id) {
        return compteRenduRepository.findById(id);
    }

    public List<CompteRendu> getComptesRendusByStatut(String statut) {
        return compteRenduRepository.findByStatut(statut);
    }

    public CompteRendu createCompteRendu(CompteRendu compteRendu) {
        return compteRenduRepository.save(compteRendu);
    }

    public void deleteCompteRendu(Long id) {
        compteRenduRepository.deleteById(id);
    }
}
