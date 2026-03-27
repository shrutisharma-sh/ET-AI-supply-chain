package com.shruti.supply_chain.services;

import com.shruti.supply_chain.dto.ProductRequest;
import com.shruti.supply_chain.dto.ProductResponse;
import com.shruti.supply_chain.dto.SupplierProductResponse;
import com.shruti.supply_chain.model.ProductStatus;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    List<ProductResponse> getProductsByCategory(Long categoryId);

    ProductResponse updateProductStatus(Long id, ProductStatus status);

    List<ProductResponse> getAllActiveProducts();


}