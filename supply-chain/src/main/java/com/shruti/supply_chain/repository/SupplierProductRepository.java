package com.shruti.supply_chain.repository;

import com.shruti.supply_chain.model.ApprovalStatus;
import com.shruti.supply_chain.model.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Long> {

    List<SupplierProduct> findByProductId(Long productId);

    List<SupplierProduct> findBySupplierId(Long supplierId);

    List<SupplierProduct> findByApprovalStatus(ApprovalStatus status);
}