package com.shruti.supply_chain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SupplierProductRequest {

    private Long supplierId;

    private Long productId;

    private BigDecimal supplyPrice;

    private Integer minimumOrderQuantity;

    private Integer leadTimeDays;

}