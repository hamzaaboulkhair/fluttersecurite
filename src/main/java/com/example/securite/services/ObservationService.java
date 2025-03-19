package com.example.securite.services;
import com.example.securite.repositories.ObservationRepository;
import com.example.securite.entities.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

    public List<Observation> getAllObservations() {
        return observationRepository.findAll();
    }

    public Optional<Observation> getObservationById(Long id) {
        return observationRepository.findById(id);
    }

    public List<Observation> getObservationsByType(String type) {
        return observationRepository.findByType(type);
    }

    public Observation createObservation(Observation observation) {
        return observationRepository.save(observation);
    }

    public void deleteObservation(Long id) {
        observationRepository.deleteById(id);
    }
}

