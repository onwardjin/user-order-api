package com.example.userorder.common.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("INVALID_LOGIN");
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
