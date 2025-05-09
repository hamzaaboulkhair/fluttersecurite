package com.example.securite.services;

import com.example.securite.entities.ExecutionAction;
import com.example.securite.entities.PlanAction;
import com.example.securite.repositories.ExecutionActionRepository;
import com.example.securite.repositories.PlanActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutionActionService {
    @Autowired
    PlanActionRepository planactionRepository;
    @Autowired
    ExecutionActionRepository executionActionRepository;
    public ExecutionAction createExecutionAction(
            ExecutionAction executionAction, Long actionId, List<String> filePaths) {

        PlanAction action = planactionRepository.findById(actionId)
                .orElseThrow(() -> new RuntimeException("Action non trouvée"));

        executionAction.setAction(action);
        executionAction.setMedias(filePaths);
        return executionActionRepository.save(executionAction);
    }

}
