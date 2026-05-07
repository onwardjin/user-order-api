package com.example.userorder.dto.product;

public record ProductSearchCondition(
        String name,
        Integer minPrice,
        Integer maxPrice
) {
}