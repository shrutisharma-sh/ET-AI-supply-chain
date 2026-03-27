package com.shruti.supply_chain.services;

import com.shruti.supply_chain.dto.OrderRequest;
import com.shruti.supply_chain.dto.OrderResponse;
import com.shruti.supply_chain.model.*;
import com.shruti.supply_chain.repository.OrderRepository;
import com.shruti.supply_chain.repository.SupplierProductRepository;
import com.shruti.supply_chain.repository.SupplierProfileRepository;
import com.shruti.supply_chain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shruti.supply_chain.model.OrderStatus;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final SupplierProductRepository supplierProductRepository;
    private final OrderRepository orderRepository;
    private final SupplierProfileRepository supplierProfileRepository;


    @Override
    public OrderResponse placeOrder(Long userId, OrderRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SupplierProduct sp = supplierProductRepository.findById(request.getSupplierProductId())
                .orElseThrow(() -> new RuntimeException("Supplier product not found"));

        if (sp.getApprovalStatus() != ApprovalStatus.APPROVED) {
            throw new RuntimeException("Product not approved yet");
        }

        double totalPrice = sp.getSupplyPrice().doubleValue() * request.getQuantity();

        Order order = Order.builder()
                .user(user)
                .supplierProduct(sp)
                .supplier(sp.getSupplier())
                .quantity(request.getQuantity())
                .totalPrice(totalPrice)
                .build();

        orderRepository.save(order);

        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getMyOrders(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getOrdersForSupplier(String email, int page, int size, String status) {

        SupplierProfile supplier = supplierProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Pageable pageable = PageRequest.of(page, size);


        if (status != null) {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());

            return orderRepository
                    .findBySupplierIdAndStatus(supplier.getId(), orderStatus, pageable)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
        }


        return orderRepository
                .findBySupplierId(supplier.getId(), pageable)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status ,String email) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));



        if (!order.getSupplier().getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized: You cannot update this order");
        }
        OrderStatus newStatus = status;



        if (newStatus == OrderStatus.ACCEPTED && order.getStatus() != OrderStatus.PLACED) {
            throw new RuntimeException("Only placed orders can be accepted");
        }
        if (newStatus == OrderStatus.REJECTED && order.getStatus() != OrderStatus.PLACED) {
            throw new RuntimeException("Only placed orders can be rejected");
        }
        if (newStatus == OrderStatus.SHIPPED && order.getStatus() != OrderStatus.ACCEPTED) {
            throw new RuntimeException("Order must be accepted first");
        }
        if (newStatus == OrderStatus.DELIVERED && order.getStatus() != OrderStatus.SHIPPED) {
            throw new RuntimeException("Order must be shipped first");
        }

        if (newStatus == OrderStatus.ACCEPTED) {
            order.setAcceptedAt(LocalDateTime.now());
        }

        if (newStatus == OrderStatus.SHIPPED) {
            order.setShippedAt(LocalDateTime.now());
        }

        if (newStatus == OrderStatus.DELIVERED) {
            order.setDeliveredAt(LocalDateTime.now());
        }
        order.setStatus(newStatus);

        orderRepository.save(order);

        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .productName(order.getSupplierProduct().getProduct().getName())
                .supplierName(order.getSupplierProduct().getSupplier().getUser().getName())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }
}
