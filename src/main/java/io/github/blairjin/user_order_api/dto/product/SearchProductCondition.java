package io.github.blairjin.user_order_api.dto.product;

public record SearchProductCondition(
        String keyword,
        Long minPrice,
        Long maxPrice
) {
}