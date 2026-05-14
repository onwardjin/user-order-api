package com.example.userorder.common.exception;

public class DuplicateLoginIdException extends RuntimeException {
    public DuplicateLoginIdException() {
        super("DUPLICATE_LOGIN_ID");
    }

    public DuplicateLoginIdException(String message) {
        super(message);
    }
}