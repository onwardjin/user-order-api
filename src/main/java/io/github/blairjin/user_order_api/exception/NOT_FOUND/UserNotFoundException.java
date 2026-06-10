package io.github.blairjin.user_order_api.exception.NOT_FOUND;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("USER_NOT_FOUND");
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}