package com.example.userorder.common.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("PRODUCT_NOT_FOUND");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
