package com.shruti.supply_chain.dto;

import com.shruti.supply_chain.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {


    private String name;

    @NotBlank
    private String email;
    private String password;
    private Role role;

    @NotBlank
    private String companyName;
}