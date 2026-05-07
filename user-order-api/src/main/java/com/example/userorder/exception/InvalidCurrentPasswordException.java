package com.example.userorder.exception;

public class InvalidCurrentPasswordException extends RuntimeException {
    public InvalidCurrentPasswordException() {
        super("Current password is incorrect");
    }

    public InvalidCurrentPasswordException(String message) {
        super(message);
    }
}
