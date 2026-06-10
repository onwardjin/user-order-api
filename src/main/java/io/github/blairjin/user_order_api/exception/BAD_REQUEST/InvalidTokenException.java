package io.github.blairjin.user_order_api.exception.BAD_REQUEST;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("INVALID_TOKEN");
    }
    public InvalidTokenException(String message) {
        super(message);
    }
}
