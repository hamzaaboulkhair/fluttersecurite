package com.example.securite.services;

import com.example.securite.entities.Responsable;
import com.example.securite.repositories.ResponsableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ResponsableService {

    @Autowired
    private ResponsableRepository responsableRepository;

    public List<Responsable> getAllResponsables() {
        return responsableRepository.findAll();
    }

    public Optional<Responsable> getResponsableById(Long id) {
        return responsableRepository.findById(id);
    }

    public Responsable createResponsable(Responsable responsable) {
        return responsableRepository.save(responsable);
    }

    public void deleteResponsable(Long id) {
        responsableRepository.deleteById(id);
    }
}

