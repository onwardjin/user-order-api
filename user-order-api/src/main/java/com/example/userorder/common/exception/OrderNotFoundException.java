package com.example.userorder.common.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("ORDER_NOT_FOUND");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
