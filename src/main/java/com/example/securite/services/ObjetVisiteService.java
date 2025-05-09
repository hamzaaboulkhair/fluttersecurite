package com.example.securite.services;

import com.example.securite.entities.ObjetVisite;
import com.example.securite.repositories.ObjetVisiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ObjetVisiteService {

    @Autowired
    private ObjetVisiteRepository objetVisiteRepository;

    public List<ObjetVisite> getAllObjetsVisite() {
        return objetVisiteRepository.findAll();
    }

    public Optional<ObjetVisite> getObjetVisiteById(Long id) {
        return objetVisiteRepository.findById(id);
    }

    public List<ObjetVisite> getObjetsVisiteByLocalisation(String localisation) {
        return objetVisiteRepository.findByLocalisation(localisation);
    }

    public List<ObjetVisite> getObjetsVisiteByNatureTravaux(String natureTravaux) {
        return objetVisiteRepository.findByNatureTravaux(natureTravaux);
    }

    public ObjetVisite createObjetVisite(ObjetVisite objetVisite) {
        return objetVisiteRepository.save(objetVisite);
    }

    public void deleteObjetVisite(Long id) {
        objetVisiteRepository.deleteById(id);
    }

    public ObjetVisite updateObjetVisite(ObjetVisite objetVisite) {
        return objetVisiteRepository.save(objetVisite);
    }

    public List<ObjetVisite> getObjetsVisiteByApsId(long apsId) {
        return objetVisiteRepository.findByApsId(apsId);
    }
}

