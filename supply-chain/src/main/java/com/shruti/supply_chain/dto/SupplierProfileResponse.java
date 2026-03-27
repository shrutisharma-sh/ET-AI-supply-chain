package com.shruti.supply_chain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierProfileResponse {

    private Long id;

    private String companyName;

    private String phone;

    private String address;

}