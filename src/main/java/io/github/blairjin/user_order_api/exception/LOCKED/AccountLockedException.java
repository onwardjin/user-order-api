package io.github.blairjin.user_order_api.exception.LOCKED;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException() {
        super("ACCOUNT_LOCKED");
    }
    public AccountLockedException(String message) {
        super(message);
    }
}
