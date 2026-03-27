package com.shruti.supply_chain.services;

import com.shruti.supply_chain.dto.CategoryRequest;
import com.shruti.supply_chain.dto.CategoryResponse;
import com.shruti.supply_chain.model.Category;
import com.shruti.supply_chain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;




    public CategoryResponse createCategory(CategoryRequest request) {

        categoryRepository.findByNameIgnoreCase(request.getName())
                .ifPresent(c -> {
                    throw new RuntimeException("Category already exists");
                });

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());


        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parent);
        }

        Category saved = categoryRepository.save(category);

        return mapToResponse(saved);
    }


    public List<CategoryResponse> getAllCategories() {

        List<Category> rootCategories = categoryRepository.findByParentIsNull();

        return rootCategories
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE CATEGORY
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parent);
        }

        Category updated = categoryRepository.save(category);

        return mapToResponse(updated);
    }


    public void deleteCategory(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.deleteById(id);
    }


    private CategoryResponse mapToResponse(Category category) {

        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setCreatedAt(category.getCreatedAt());

        if (category.getParent() != null) {
            response.setParentId(category.getParent().getId());
        }

        if (category.getSubCategories() != null) {
            response.setSubCategories(
                    category.getSubCategories()
                            .stream()
                            .map(this::mapToResponse)
                            .toList()
            );
        }

        return response;
    }
}