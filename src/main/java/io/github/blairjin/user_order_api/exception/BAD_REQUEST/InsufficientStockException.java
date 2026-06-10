package io.github.blairjin.user_order_api.exception.BAD_REQUEST;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("Insufficient_Stock");
    }
    public InsufficientStockException(String message) {
        super(message);
    }
}
