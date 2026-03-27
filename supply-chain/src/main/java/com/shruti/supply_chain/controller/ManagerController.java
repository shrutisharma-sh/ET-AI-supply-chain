package com.shruti.supply_chain.controller;

import com.shruti.supply_chain.dto.SupplierProductResponse;
import com.shruti.supply_chain.services.SupplierProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final SupplierProductService supplierProductService;



    @GetMapping("/pending-products")
    public ResponseEntity<List<SupplierProductResponse>> getPendingProducts() {
        return ResponseEntity.ok(
                supplierProductService.getPendingProducts()
        );
    }
    @PutMapping("/supplier-products/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        supplierProductService.approveSupplierProduct(id);
        return ResponseEntity.ok("Supplier product approved");
    }

    @PutMapping("/supplier-products/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        supplierProductService.rejectSupplierProduct(id);
        return ResponseEntity.ok("Supplier product rejected");
    }
}