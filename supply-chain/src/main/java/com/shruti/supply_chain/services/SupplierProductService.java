package com.shruti.supply_chain.services;

import com.shruti.supply_chain.dto.SupplierProductRequest;
import com.shruti.supply_chain.dto.SupplierProductResponse;

import java.util.List;

public interface SupplierProductService {

    SupplierProductResponse addProductToSupplier(Long supplierId, SupplierProductRequest request);

    SupplierProductResponse updateSupplierProduct(Long id, SupplierProductRequest request);

    List<SupplierProductResponse> getProductsBySupplier(Long supplierId);

    List<SupplierProductResponse> getSuppliersByProduct(Long productId);

    void deleteSupplierProduct(Long id);

    void approveSupplierProduct(Long id);

    void rejectSupplierProduct(Long id);
    List<SupplierProductResponse> getPendingProducts();
}