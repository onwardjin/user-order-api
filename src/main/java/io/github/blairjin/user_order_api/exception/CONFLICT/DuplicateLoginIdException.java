package io.github.blairjin.user_order_api.exception.CONFLICT;

public class DuplicateLoginIdException extends RuntimeException {
    public DuplicateLoginIdException() {
        super("DUPLICATE_LOGIN_ID");
    }
    public DuplicateLoginIdException(String message) {
        super(message);
    }
}
