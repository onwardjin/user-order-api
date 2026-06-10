package io.github.blairjin.user_order_api.dto.product;

import io.github.blairjin.user_order_api.domain.product.ProductStatus;

public record ProductUpdateRequest(
        String productName,
        Integer stockQuantity,
        Long unitPrice,
        ProductStatus productStatus
) {
}