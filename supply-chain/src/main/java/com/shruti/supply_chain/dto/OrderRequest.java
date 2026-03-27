package com.shruti.supply_chain.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long supplierProductId;
    private Integer quantity;
}