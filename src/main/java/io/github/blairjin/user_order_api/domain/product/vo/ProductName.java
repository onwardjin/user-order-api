package io.github.blairjin.user_order_api.domain.product.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

public record ProductName(
        String value
) {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 100;

    public ProductName {
        if (value == null) {
            throw new InvalidValueException(
                    "ProductName", "Product name cannot be null."
            );
        }

        if (value.isBlank()) {
            throw new InvalidValueException(
                    "ProductName", "Product name cannot be blank."
            );
        }

        if (value.length() < MIN_LENGTH) {
            throw new InvalidValueException(
                    "ProductName", "Product name must be at least 3 characters long."
            );
        }

        if (value.length() > MAX_LENGTH) {
            throw new InvalidValueException(
                    "ProductName", "Product name must not exceed 100 characters."
            );
        }
    }

    public static ProductName of(String value){
        return new ProductName(value);
    }
}