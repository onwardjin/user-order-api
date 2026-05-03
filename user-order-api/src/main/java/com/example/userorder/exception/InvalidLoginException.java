package com.example.userorder.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("Invalid login ID or password");
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
