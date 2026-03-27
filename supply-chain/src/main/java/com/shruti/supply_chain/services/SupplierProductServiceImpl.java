package com.shruti.supply_chain.services;

import com.shruti.supply_chain.dto.SupplierProductRequest;
import com.shruti.supply_chain.dto.SupplierProductResponse;
import com.shruti.supply_chain.model.ApprovalStatus;
import com.shruti.supply_chain.model.Product;
import com.shruti.supply_chain.model.SupplierProduct;
import com.shruti.supply_chain.model.SupplierProfile;
import com.shruti.supply_chain.repository.ProductRepository;
import com.shruti.supply_chain.repository.SupplierProductRepository;
import com.shruti.supply_chain.repository.SupplierProfileRepository;
import com.shruti.supply_chain.services.SupplierProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierProductServiceImpl implements SupplierProductService {

    private final SupplierProductRepository supplierProductRepository;
    private final SupplierProfileRepository supplierRepository;
    private final ProductRepository productRepository;

    @Override
    public SupplierProductResponse addProductToSupplier(Long supplierId, SupplierProductRequest request) {

        SupplierProfile supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        SupplierProduct supplierProduct = SupplierProduct.builder()
                .supplier(supplier)
                .product(product)
                .supplyPrice(request.getSupplyPrice())
                .minimumOrderQuantity(request.getMinimumOrderQuantity())
                .leadTimeDays(request.getLeadTimeDays())
                .build();

        supplierProductRepository.save(supplierProduct);

        return mapToResponse(supplierProduct);
    }

    @Override
    public SupplierProductResponse updateSupplierProduct(Long id, SupplierProductRequest request) {

        SupplierProduct supplierProduct = supplierProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier product not found"));

        supplierProduct.setSupplyPrice(request.getSupplyPrice());
        supplierProduct.setMinimumOrderQuantity(request.getMinimumOrderQuantity());
        supplierProduct.setLeadTimeDays(request.getLeadTimeDays());

        supplierProductRepository.save(supplierProduct);

        return mapToResponse(supplierProduct);
    }

    @Override
    public List<SupplierProductResponse> getProductsBySupplier(Long supplierId) {

        return supplierProductRepository.findBySupplierId(supplierId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierProductResponse> getSuppliersByProduct(Long productId) {

        return supplierProductRepository.findByProductId(productId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSupplierProduct(Long id) {

        SupplierProduct supplierProduct = supplierProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier product not found"));

        supplierProductRepository.delete(supplierProduct);
    }

    private SupplierProductResponse mapToResponse(SupplierProduct sp) {

        return SupplierProductResponse.builder()
                .id(sp.getId())
                .productName(sp.getProduct().getName())
                .supplyPrice(sp.getSupplyPrice())
                .minimumOrderQuantity(sp.getMinimumOrderQuantity())
                .leadTimeDays(sp.getLeadTimeDays())
                .build();
    }
    @Override
    public List<SupplierProductResponse> getPendingProducts() {
        return supplierProductRepository
                .findByApprovalStatus(ApprovalStatus.PENDING)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void approveSupplierProduct(Long id) {
        SupplierProduct sp = supplierProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier product not found"));

        sp.setApprovalStatus(ApprovalStatus.APPROVED);
        supplierProductRepository.save(sp);
    }

    @Override
    public void rejectSupplierProduct(Long id) {
        SupplierProduct sp = supplierProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier product not found"));

        sp.setApprovalStatus(ApprovalStatus.REJECTED);
        supplierProductRepository.save(sp);
    }
}