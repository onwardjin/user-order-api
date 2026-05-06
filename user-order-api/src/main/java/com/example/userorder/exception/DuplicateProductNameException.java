package com.example.userorder.exception;

public class DuplicateProductNameException extends RuntimeException {
    public DuplicateProductNameException() {
        super("Product Name is already exists");
    }

    public DuplicateProductNameException(String message) {
        super(message);
    }
}
