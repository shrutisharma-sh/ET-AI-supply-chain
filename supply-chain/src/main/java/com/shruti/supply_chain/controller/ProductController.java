package com.shruti.supply_chain.controller;

import com.shruti.supply_chain.dto.ProductRequest;
import com.shruti.supply_chain.dto.ProductResponse;
import com.shruti.supply_chain.dto.ProductStatusUpdateRequest;
import com.shruti.supply_chain.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/admin/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {

        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {

        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/public/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        List<ProductResponse> products = productService.getAllActiveProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {

        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/manager/products/{id}/status")
    public ResponseEntity<String> updateProductStatus(
            @PathVariable Long id,
            @RequestBody ProductStatusUpdateRequest request) {

        productService.updateProductStatus(id, request.getStatus());
        return ResponseEntity.ok("Product status updated successfully");
    }


}
