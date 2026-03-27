package com.shruti.supply_chain.agent;

import com.shruti.supply_chain.agent.AgentAuditLog;
import com.shruti.supply_chain.model.Order;
import com.shruti.supply_chain.agent.AgentAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AgentAuditRepository auditRepository;

    public void log(Order order, String decision, String action) {

        AgentAuditLog log = new AgentAuditLog();

        log.setOrderId(order.getId());
        log.setIssue("Detected issue in order with status: " + order.getStatus());
        log.setDecision(decision);
        log.setAction(action);

        auditRepository.save(log);

        // optional console (good for demo)
        System.out.println("==== AUDIT LOG SAVED ====");
        System.out.println(log);
    }
}