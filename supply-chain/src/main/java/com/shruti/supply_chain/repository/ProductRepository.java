package com.shruti.supply_chain.repository;

import com.shruti.supply_chain.model.Product;
import com.shruti.supply_chain.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByStatus(ProductStatus status);

}