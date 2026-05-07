package com.example.userorder.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("Invalid login credentials");
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
