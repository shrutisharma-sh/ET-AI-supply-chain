package com.shruti.supply_chain.agent;

import com.shruti.supply_chain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DecisionService {

    public String makeDecision(Order order){


        if (order.getStatus().name().equals("SHIPPED") &&
                order.getCreatedAt().isBefore(java.time.LocalDateTime.now().minusDays(3))) {
            return "escalate";
        }


        if (order.getQuantity() >= 5) {
            return "mark priority";
        }


        if (order.getStatus().name().equals("PLACED") &&
                order.getCreatedAt().isBefore(java.time.LocalDateTime.now().minusDays(2))) {
            return "reorder";
        }


        if (order.getSupplierProduct().getLeadTimeDays() > 5) {
            return "switch supplier";
        }

        return "mark priority";
    }
}