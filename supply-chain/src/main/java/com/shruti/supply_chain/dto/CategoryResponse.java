package com.shruti.supply_chain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryResponse {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private Long parentId;

    private List<CategoryResponse> subCategories;

    private LocalDateTime createdAt;

}