package com.shruti.supply_chain.agent;

import com.shruti.supply_chain.agents.ActionAgent;
import com.shruti.supply_chain.agents.ActionRequest;
import com.shruti.supply_chain.agents.ActionType;
import com.shruti.supply_chain.model.Order;
import com.shruti.supply_chain.services.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentOrchestratorService {

    private final FailureDetectionService failureDetectionService;
    private final DecisionService decisionService;
    private final ActionAgent actionAgent;
    private final AuditService auditService;


    public String runAgent() {

        List<Order> problemOrders = failureDetectionService.detectIssues();

        if (problemOrders.isEmpty()) {
            return " No issues detected";
        }

        StringBuilder finalLog = new StringBuilder();

        for (Order order : problemOrders) {


            String decision = decisionService.makeDecision(order);


            ActionType actionType = mapDecisionToAction(decision);


            ActionRequest request = new ActionRequest();
            request.setOrderId(order.getId());
            request.setActionType(actionType);
            request.setReason(decision);

            String actionResult = actionAgent.execute(request);


            auditService.log(order, decision, actionResult);


            finalLog.append("\nOrder ID: ").append(order.getId())
                    .append("\nDecision: ").append(decision)
                    .append("\nAction: ").append(actionResult)
                    .append("\n----------------------\n");
        }

        return finalLog.toString();
    }


    private ActionType mapDecisionToAction(String decision) {

        decision = decision.toLowerCase();

        if (decision.contains("reorder")) {
            return ActionType.REORDER;
        } else if (decision.contains("switch")) {
            return ActionType.SWITCH_SUPPLIER;
        } else if (decision.contains("escalate")) {
            return ActionType.ESCALATE;
        } else {
            return ActionType.MARK_PRIORITY;
        }
    }
}