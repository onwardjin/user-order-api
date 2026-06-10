package io.github.blairjin.user_order_api.dto.common;

public record ErrorResponse(
        int status,
        String message
) {
}