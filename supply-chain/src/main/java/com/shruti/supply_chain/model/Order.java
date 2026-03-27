package com.shruti.supply_chain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "supplier_product_id", nullable = false)
    private SupplierProduct supplierProduct;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierProfile supplier;

    private Integer quantity;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime acceptedAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;

    private boolean priority;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PLACED;
    }
}