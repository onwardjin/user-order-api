package io.github.blairjin.user_order_api.domain.order.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

public record OrderQuantity(
        int value
) {
    public OrderQuantity{
        if (value <= 0) {
            throw new InvalidValueException(
                    "OrderQuantity", "Order quantity must be greater than zero."
            );
        }
    }

    public static OrderQuantity of(int value){
        return new OrderQuantity(value);
    }
}