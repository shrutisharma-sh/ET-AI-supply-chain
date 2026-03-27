package com.shruti.supply_chain.controller;

import com.shruti.supply_chain.dto.SupplierProductRequest;
import com.shruti.supply_chain.dto.SupplierProductResponse;
import com.shruti.supply_chain.model.SupplierProfile;
import com.shruti.supply_chain.repository.SupplierProfileRepository;
import com.shruti.supply_chain.repository.UserRepository;
import com.shruti.supply_chain.services.SupplierProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SupplierProductController {

    private final SupplierProductService supplierProductService;
    private final UserRepository userRepository;
    private final SupplierProfileRepository supplierRepository;

    @PostMapping("/supplier/products")
    public ResponseEntity<SupplierProductResponse> addProduct(
            Authentication authentication,
            @RequestBody SupplierProductRequest request) {

        String email = authentication.getName();

        SupplierProfile supplier = supplierRepository
                .findByUserId(userRepository.findByEmail(email).orElseThrow().getId())
                .orElseThrow(() -> new RuntimeException("Supplier profile not found"));

        SupplierProductResponse response =
                supplierProductService.addProductToSupplier(supplier.getId(), request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/supplier/my-products")
    public ResponseEntity<List<SupplierProductResponse>> getMyProducts(Authentication authentication) {

        String email = authentication.getName();

        SupplierProfile supplier = supplierRepository
                .findByUserId(userRepository.findByEmail(email).orElseThrow().getId())
                .orElseThrow(() -> new RuntimeException("Supplier profile not found"));

        List<SupplierProductResponse> products =
                supplierProductService.getProductsBySupplier(supplier.getId());

        return ResponseEntity.ok(products);
    }

    @PutMapping("/supplier/products/{id}")
    public ResponseEntity<SupplierProductResponse> updateSupplierProduct(
            @PathVariable Long id,
            @RequestBody SupplierProductRequest request) {

        SupplierProductResponse response =
                supplierProductService.updateSupplierProduct(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/supplier/products/{id}")
    public ResponseEntity<String> deleteSupplierProduct(@PathVariable Long id) {

        supplierProductService.deleteSupplierProduct(id);

        return ResponseEntity.ok("Supplier product deleted successfully");
    }

    @GetMapping("/public/products/{productId}/suppliers")
    public ResponseEntity<List<SupplierProductResponse>> getSuppliersForProduct(
            @PathVariable Long productId) {

        List<SupplierProductResponse> suppliers =
                supplierProductService.getSuppliersByProduct(productId);

        return ResponseEntity.ok(suppliers);
    }
}
