package io.github.blairjin.user_order_api.exception.BAD_REQUEST;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(){
        super("INVALID_VALUE");
    }

    public InvalidValueException(String field, String reason){
        super(field + ": " + reason);
    }
}
