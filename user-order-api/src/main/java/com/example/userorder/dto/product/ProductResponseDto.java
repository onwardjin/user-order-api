package com.example.userorder.dto.product;

import com.example.userorder.entity.Product;

public record ProductResponseDto(
        Long id,
        String name,
        Integer unitPrice,
        Integer stockQuantity
) {
    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getUnitPrice(),
                product.getStockQuantity()
        );
    }
}