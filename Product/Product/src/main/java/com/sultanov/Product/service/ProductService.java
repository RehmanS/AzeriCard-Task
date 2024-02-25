package com.sultanov.Product.service;

import com.sultanov.Product.dto.*;
import com.sultanov.Product.entity.Product;
import com.sultanov.Product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(ProductCreateRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .stockCount(request.stockCount())
                .build();
        productRepository.save(product);
    }

    public List<ProductResponse> findAllActiveProducts() {
        return productRepository.findAllByState(1)
                .stream()
                .map(this::productToProductResponse)
                .toList();
    }

    public Boolean checkStockAvailability(CheckStockRequest request) {
        for (Item item : request.items()) {
            Product product = productRepository.findById(item.productId()).orElse(null);
            if (product == null || product.getStockCount() < item.quantity()) {
                return false;
            }
        }
        return true;
    }

    public BigDecimal getTotalPrice(GetTotalPriceRequest request) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Long productId : request.productIds()) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                totalPrice = totalPrice.add(product.getPrice());
            }
        }
        return totalPrice;
    }

    public void updateStocks(UpdateStockRequest request) {
        for (Item item : request.items()) {
            Long productId = item.productId();
            int quantity = item.quantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

            long updatedStockCount = product.getStockCount() - quantity;
            product.setStockCount(updatedStockCount);
            productRepository.save(product);
        }
    }

    private ProductResponse productToProductResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .stockCount(product.getStockCount())
                .build();
    }
}