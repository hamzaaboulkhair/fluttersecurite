package com.example.securite.services;

import com.example.securite.entities.ElementDeMesure;
import com.example.securite.entities.PlanAction;
import com.example.securite.repositories.ElementDeMesureRepository;
import com.example.securite.repositories.PlanActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementDeMesureService {

    @Autowired
    private ElementDeMesureRepository elementRepo;

    @Autowired
    private PlanActionRepository planRepo;

    public ElementDeMesure addElementDeMesure(Long planActionId, String elementEfficacité, String detailsEfficacité, String résultat, int note ) {
        PlanAction planAction = planRepo.findById(planActionId)
                .orElseThrow(() -> new RuntimeException("Plan d'action non trouvé avec id : " + planActionId));
        ElementDeMesure ElementDeMesure = new ElementDeMesure();
        ElementDeMesure.setPlanAction(planAction);
        ElementDeMesure.setElementEfficacite(elementEfficacité);
        ElementDeMesure.setDetailsEfficacite(detailsEfficacité);
        ElementDeMesure.setResultat(résultat);
        ElementDeMesure.setNote(note);
        return elementRepo.save(ElementDeMesure);
    }

    public List<ElementDeMesure> getByPlanAction(Long planActionId) {
        return elementRepo.findByPlanActionId(planActionId);
    }
}
