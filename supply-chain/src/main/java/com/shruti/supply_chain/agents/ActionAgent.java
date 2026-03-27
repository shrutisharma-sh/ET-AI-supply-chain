package com.shruti.supply_chain.agents;

import com.shruti.supply_chain.model.Order;
import com.shruti.supply_chain.model.OrderStatus;
import com.shruti.supply_chain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionAgent {

    private final OrderRepository orderRepository;

    public String execute(ActionRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        switch (request.getActionType()) {

            case MARK_PRIORITY:
                order.setPriority(true);
                orderRepository.save(order);
                return "Order marked as HIGH PRIORITY";

            case ESCALATE:

                order.setPriority(true);
                orderRepository.save(order);
                return "Order escalated (priority + manager attention)";

            case REORDER:

                order.setStatus(OrderStatus.PLACED);
                orderRepository.save(order);
                return "Order re-initiated (status → PLACED)";

            case SWITCH_SUPPLIER:

                order.setPriority(true);
                orderRepository.save(order);
                return "Supplier switch simulated (priority set)";

            default:
                return "No action executed";
        }
    }
}