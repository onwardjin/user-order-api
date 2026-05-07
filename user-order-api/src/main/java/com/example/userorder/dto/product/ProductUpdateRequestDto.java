package com.example.userorder.dto.product;

public record ProductUpdateRequestDto(
        String name,
        Integer unitPrice,
        Integer stockQuantity
) {
}