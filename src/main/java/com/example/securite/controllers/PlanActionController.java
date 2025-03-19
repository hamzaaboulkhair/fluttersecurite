package com.example.securite.controllers;

import com.example.securite.entities.PlanAction;
import com.example.securite.services.PlanActionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans-action")
public class PlanActionController {

    @Autowired
    private PlanActionService planActionService;

    @GetMapping
    public List<PlanAction> getAllPlansAction() {
        return planActionService.getAllPlansAction();
    }

    @GetMapping("/{id}")
    public Optional<PlanAction> getPlanActionById(@PathVariable Long id) {
        return planActionService.getPlanActionById(id);
    }

    @GetMapping("/{compteRenduId}")
    public List<PlanAction> getPlansByCompteRendu(@PathVariable Long compteRenduId) {
        return planActionService.getPlansByCompteRendu(compteRenduId);
    }

    @PostMapping
    public PlanAction createPlanAction(@RequestBody PlanAction planAction) {
        return planActionService.createPlanAction(planAction);
    }

    @DeleteMapping("/{id}")
    public void deletePlanAction(@PathVariable Long id) {
        planActionService.deletePlanAction(id);
    }
}
