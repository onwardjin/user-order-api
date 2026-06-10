package io.github.blairjin.user_order_api.domain.product.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

public record StockQuantity(
        int value
) {
    public StockQuantity {
        if (value < 0) {
            throw new InvalidValueException(
                    "StockQuantity", "Stock quantity cannot be negative."
            );
        }
    }

    public static StockQuantity of(int value){
        return new StockQuantity(value);
    }
}