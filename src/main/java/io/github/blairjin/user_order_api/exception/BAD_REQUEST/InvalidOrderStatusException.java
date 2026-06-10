package io.github.blairjin.user_order_api.exception.BAD_REQUEST;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException() {
        super("INVALID_ORDER_STATUS");
    }
    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
