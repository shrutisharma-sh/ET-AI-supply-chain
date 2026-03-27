package com.shruti.supply_chain.agent;

import com.shruti.supply_chain.model.Order;
import com.shruti.supply_chain.model.OrderStatus;
import com.shruti.supply_chain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FailureDetectionService {

    private final OrderRepository orderRepository;

    public List<Order> detectIssues(){

        List<Order> problemOrders = new ArrayList<>();

        List<Order> allOrders = orderRepository.findAll();

        for (Order order : allOrders) {


            if (order.getStatus() == OrderStatus.SHIPPED &&
                    order.getCreatedAt().isBefore(LocalDateTime.now().minusDays(3))) {

                problemOrders.add(order);
            }


            if (order.getStatus() == OrderStatus.PLACED &&
                    order.getCreatedAt().isBefore(LocalDateTime.now().minusDays(2))) {

                problemOrders.add(order);
            }
        }

        return problemOrders;
    }
}