package io.github.blairjin.user_order_api.exception.NOT_FOUND;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException() {
        super("ORDER_NOT_FOUND");
    }
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}