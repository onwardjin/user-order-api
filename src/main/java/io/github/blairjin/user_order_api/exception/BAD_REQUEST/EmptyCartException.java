package io.github.blairjin.user_order_api.exception.BAD_REQUEST;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException() {
        super("EMPTY_CART");
    }
    public EmptyCartException(String message) {
        super(message);
    }
}
