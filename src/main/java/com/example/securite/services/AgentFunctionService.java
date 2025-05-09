package com.example.securite.services;

import com.example.securite.entities.AgentFunction;
import com.example.securite.entities.VisiteSecurite;
import com.example.securite.repositories.AgentFunctionRepository;
import com.example.securite.repositories.VisiteSecuriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentFunctionService {

    @Autowired
    private AgentFunctionRepository agentFunctionRepository;

    @Autowired
    private VisiteSecuriteRepository visiteSecuriteRepository;

    // Add an agent function to a visit
    public AgentFunction addAgentFunction(Long visiteId, String functionName) {
        VisiteSecurite visite = visiteSecuriteRepository.findById(visiteId)
                .orElseThrow(() -> new RuntimeException("Visite not found"));

        // Make sure functionName is handled as a plain string and not a JSON object
        AgentFunction agentFunction = new AgentFunction();
        agentFunction.setFunctionName(functionName);
        agentFunction.setVisiteSecurite(visite);

        return agentFunctionRepository.save(agentFunction);
    }

    // Get all agent functions for a specific visit
    public List<AgentFunction> getAgentFunctionsForVisite(Long visiteId) {
        return agentFunctionRepository.findByVisiteSecuriteId(visiteId);
    }

    public void deleteAgentFunctionByName(String functionName) {
        // Find the AgentFunction by functionName
        AgentFunction agentFunction = (AgentFunction) agentFunctionRepository.findByFunctionName(functionName)
                .orElseThrow(() -> new RuntimeException("AgentFunction not found"));

        // Delete the found AgentFunction
        agentFunctionRepository.delete(agentFunction);
    }

    public void deleteAgentFunction(Long visiteId, Long functionId) {
        AgentFunction agentFunction = agentFunctionRepository.findById(functionId)
                .orElseThrow(() -> new RuntimeException("AgentFunction not found"));

        // Ensure the function belongs to the correct visit
        if (!agentFunction.getVisiteSecurite().getId().equals(visiteId)) {
            throw new RuntimeException("Function does not belong to the specified visit");
        }

        agentFunctionRepository.delete(agentFunction);
    }
}

