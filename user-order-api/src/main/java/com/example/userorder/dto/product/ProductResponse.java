package com.example.userorder.dto.product;

import com.example.userorder.domain.product.Product;

public record ProductResponse(
        Long id,
        String name,
        long unitPrice,
        int stockQuantity
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName().value(),
                product.getUnitPrice().value(),
                product.getStockQuantity().value()
        );
    }
}