package com.shruti.supply_chain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SupplierProductResponse {

    private Long id;

    private Long supplierId;

    private String supplierCompany;

    private Long productId;

    private String productName;

    private BigDecimal supplyPrice;

    private Integer minimumOrderQuantity;

    private Integer leadTimeDays;

}