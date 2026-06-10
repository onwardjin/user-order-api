package io.github.blairjin.user_order_api.exception.BAD_REQUEST;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("INVALID_LOGIN");
    }
    public InvalidLoginException(String message) {
        super(message);
    }
}
