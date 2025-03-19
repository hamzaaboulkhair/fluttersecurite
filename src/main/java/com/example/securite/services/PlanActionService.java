package com.example.securite.services;

import com.example.securite.entities.PlanAction;
import com.example.securite.repositories.PlanActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlanActionService {

    @Autowired
    private PlanActionRepository planActionRepository;

    public List<PlanAction> getAllPlansAction() {
        return planActionRepository.findAll();
    }

    public Optional<PlanAction> getPlanActionById(Long id) {
        return planActionRepository.findById(id);
    }

    public List<PlanAction> getPlansByCompteRendu(Long compteRenduId) {
        return planActionRepository.findByCompteRenduId(compteRenduId);
    }

    public PlanAction createPlanAction(PlanAction planAction) {
        return planActionRepository.save(planAction);
    }

    public void deletePlanAction(Long id) {
        planActionRepository.deleteById(id);
    }
}
