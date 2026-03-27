package com.shruti.supply_chain.dto;

import com.shruti.supply_chain.model.ProductStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String sku;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private ProductStatus status;
    private String categoryName;
}