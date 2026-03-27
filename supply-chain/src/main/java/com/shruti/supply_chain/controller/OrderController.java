package com.shruti.supply_chain.controller;

import com.shruti.supply_chain.dto.OrderRequest;
import com.shruti.supply_chain.dto.OrderResponse;
import com.shruti.supply_chain.model.User;
import com.shruti.supply_chain.repository.UserRepository;
import com.shruti.supply_chain.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            Authentication authentication,
            @RequestBody OrderRequest request) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        OrderResponse response =
                orderService.placeOrder(user.getId(), request);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderResponse> orders =
                orderService.getMyOrders(user.getId());

        return ResponseEntity.ok(orders);
    }
}