package com.example.userorder.dto.product;

public record ProductSearchCondition(
        Long id,
        String name,
        Integer minPrice,
        Integer maxPrice
) {
}