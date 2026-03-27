package com.shruti.supply_chain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierProfileRequest {

    @NotBlank
    private String companyName;


    private String phone;

    private String address;

}