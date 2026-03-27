package com.shruti.supply_chain.repository;

import com.shruti.supply_chain.model.Order;
import com.shruti.supply_chain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
    List<Order> findBySupplierId(Long supplierId, Pageable pageable);


    Page<Order> findBySupplierIdAndStatus(Long supplierId, OrderStatus status, Pageable pageable);
}