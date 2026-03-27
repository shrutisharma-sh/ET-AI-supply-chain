package com.shruti.supply_chain.services;

import com.shruti.supply_chain.dto.OrderRequest;
import com.shruti.supply_chain.dto.OrderResponse;
import com.shruti.supply_chain.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(Long userId, OrderRequest request);

    List<OrderResponse> getMyOrders(Long userId);

    public List<OrderResponse> getOrdersForSupplier(String email, int page, int size, String status);

    OrderResponse updateOrderStatus(Long orderId, OrderStatus status, String email);
}
