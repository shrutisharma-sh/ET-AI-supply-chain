package com.shruti.supply_chain.dto;

import com.shruti.supply_chain.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

    private Long id;
    private String productName;
    private String supplierName;
    private Integer quantity;
    private Double totalPrice;
    private OrderStatus status;
}