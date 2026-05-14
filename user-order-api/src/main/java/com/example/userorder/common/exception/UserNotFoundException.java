package com.example.userorder.common.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("USER_NOT_FOUND");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
