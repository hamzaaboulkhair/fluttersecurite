package com.example.securite.services;

import com.example.securite.entities.CompteRendu;
import com.example.securite.entities.PlanAction;
import com.example.securite.repositories.CompteRenduRepository;
import com.example.securite.repositories.PlanActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlanActionService {

    @Autowired
    private PlanActionRepository planActionRepository;

    @Autowired
    private CompteRenduRepository compteRenduRepository;

    public List<PlanAction> getAllPlansAction() {
        return planActionRepository.findAll();
    }

    public Optional<PlanAction> getPlanActionById(Long id) {
        return planActionRepository.findById(id);
    }

    public List<PlanAction> getPlansByCompteRendu(Long compteRenduId) {
        return planActionRepository.findByCompteRenduId(compteRenduId);
    }

    public PlanAction createPlanAction(PlanAction planAction, Long compteRenduId) {
        // Print to verify if the compte_rendu_id is being received correctly
        System.out.println("Received compte_rendu_id: " + compteRenduId);

        // Fetch the CompteRendu by its ID
        CompteRendu compteRendu = compteRenduRepository.findById(compteRenduId)
                .orElseThrow(() -> new IllegalArgumentException("CompteRendu non trouvé pour l'ID: " + compteRenduId));

        // Set the CompteRendu in the PlanAction object
        planAction.setCompteRendu(compteRendu);

        // Save the PlanAction entity in the database
        return planActionRepository.save(planAction);
    }






    public void deletePlanAction(Long id) {
        planActionRepository.deleteById(id);
    }
}
