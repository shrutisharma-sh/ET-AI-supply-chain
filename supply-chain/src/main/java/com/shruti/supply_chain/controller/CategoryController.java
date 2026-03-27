package com.shruti.supply_chain.controller;

import com.shruti.supply_chain.dto.CategoryRequest;
import com.shruti.supply_chain.dto.CategoryResponse;
import com.shruti.supply_chain.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;



    // admin only
    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.ok(response);
    }

    // for public
    @GetMapping("/public/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {

        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // update hy admin
    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest request) {

        CategoryResponse updated = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(updated);
    }

    // by admin
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}