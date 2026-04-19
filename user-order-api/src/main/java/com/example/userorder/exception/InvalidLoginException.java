package com.example.userorder.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(){
        super("Invalid Login");
    }
}
