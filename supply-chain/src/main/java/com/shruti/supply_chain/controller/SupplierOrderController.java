package com.shruti.supply_chain.controller;

import com.shruti.supply_chain.dto.OrderResponse;
import com.shruti.supply_chain.model.OrderStatus;
import com.shruti.supply_chain.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier/orders")
@RequiredArgsConstructor
public class SupplierOrderController {

    private final OrderService orderService;

    @GetMapping

    public List<OrderResponse> getSupplierOrders(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String status) {

        String email = authentication.getName();

        return orderService.getOrdersForSupplier(email, page, size, status);
    }

    @PutMapping("/{orderId}/accept")
    public OrderResponse acceptOrder(@PathVariable Long orderId,
                                     Authentication authentication) {

        String email = authentication.getName();

        return orderService.updateOrderStatus(orderId, OrderStatus.ACCEPTED, email);
    }

    @PutMapping("/{orderId}/reject")
    public OrderResponse rejectOrder(@PathVariable Long orderId,
                                     Authentication authentication) {

        String email = authentication.getName();
        return orderService.updateOrderStatus(orderId, OrderStatus.REJECTED ,email);
    }

    @PutMapping("/{orderId}/ship")
    public OrderResponse shipOrder(@PathVariable Long orderId,
                                   Authentication authentication) {

        String email = authentication.getName();
        return orderService.updateOrderStatus(orderId, OrderStatus.SHIPPED ,email);
    }

    @PutMapping("/{orderId}/deliver")
    public OrderResponse deliverOrder(@PathVariable Long orderId,
                                      Authentication authentication) {

        String email = authentication.getName();
        return orderService.updateOrderStatus(orderId, OrderStatus.DELIVERED,email);
    }
}