package com.example.securite.controllers;

import com.example.securite.entities.Observation;
import com.example.securite.services.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    @Autowired
    private ObservationService observationService;

    @GetMapping
    public List<Observation> getAllObservations() {
        return observationService.getAllObservations();
    }

    @GetMapping("/{id}")
    public Optional<Observation> getObservationById(@PathVariable Long id) {
        return observationService.getObservationById(id);
    }

    @GetMapping("/{type}")
    public List<Observation> getObservationsByType(@PathVariable String type) {
        return observationService.getObservationsByType(type);
    }

    @PostMapping
    public Observation createObservation(@RequestBody Observation observation) {
        return observationService.createObservation(observation);
    }

    @DeleteMapping("/{id}")
    public void deleteObservation(@PathVariable Long id) {
        observationService.deleteObservation(id);
    }
}

