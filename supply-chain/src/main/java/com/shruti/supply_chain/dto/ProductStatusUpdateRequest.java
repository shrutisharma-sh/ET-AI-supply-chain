package com.shruti.supply_chain.dto;

import com.shruti.supply_chain.model.ProductStatus;
import lombok.Data;

@Data
public class ProductStatusUpdateRequest {

    private ProductStatus status;

}