package com.example.userorder.exception;

public class DuplicateLoginException extends RuntimeException {
    public DuplicateLoginException() { super("Duplicate login "); }
    public DuplicateLoginException(String message) {
        super(message);
    }
}
