package com.example.securite.controllers;

import com.example.securite.entities.AgentFunction;
import com.example.securite.services.AgentFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent-functions")
public class AgentFunctionController {

    @Autowired
    private AgentFunctionService agentFunctionService;


    @Autowired
    public AgentFunctionController(AgentFunctionService agentFunctionService) {
        this.agentFunctionService = agentFunctionService;
    }

    // Fetch all agent functions for a visit
    @GetMapping("/visite/{visiteId}")
    public List<AgentFunction> getAgentFunctionsForVisite(@PathVariable Long visiteId) {
        return agentFunctionService.getAgentFunctionsForVisite(visiteId);
    }

    // Add a new agent function to a visit
    // Update the method to accept an AgentFunction object instead of just a string
    @PostMapping("/visite/{visiteId}")
    public AgentFunction addAgentFunction(@PathVariable Long visiteId, @RequestBody AgentFunction agentFunction) {
        return agentFunctionService.addAgentFunction(visiteId, agentFunction.getFunctionName());
    }

    @DeleteMapping("/visite/{visiteId}/function/{functionId}")
    public void deleteAgentFunction(@PathVariable Long visiteId, @PathVariable Long functionId) {
        agentFunctionService.deleteAgentFunction(visiteId, functionId);
    }



}
