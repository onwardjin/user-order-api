package io.github.blairjin.user_order_api.exception.NOT_FOUND;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("ORDER_NOT_FOUND");
    }
    public OrderNotFoundException(String message) {
        super(message);
    }
}