package com.shruti.supply_chain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supplier_products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"supplier_id", "product_id"})
        })
public class SupplierProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierProfile supplier;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Column(nullable = false)
    private BigDecimal supplyPrice;

    @NotNull
    private Integer minimumOrderQuantity;


    private Integer leadTimeDays;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.approvalStatus = ApprovalStatus.PENDING;
    }

}