package com.shruti.supply_chain.agents;

import lombok.Data;

@Data
public class ActionRequest {
    private Long orderId;
    private ActionType actionType;
    private String reason; // from AI decision
}