package com.shruti.supply_chain.agent;

import com.shruti.supply_chain.agents.ActionAgent;
import com.shruti.supply_chain.agents.ActionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentAuditRepository auditRepository;
    private final AgentOrchestratorService orchestratorService;

    @PostMapping("/run")
    public String runAgent() {
        return orchestratorService.runAgent();
    }
    @GetMapping("/logs")
    public List<AgentAuditLog> getLogs() {
        return auditRepository.findAll();
    }
}