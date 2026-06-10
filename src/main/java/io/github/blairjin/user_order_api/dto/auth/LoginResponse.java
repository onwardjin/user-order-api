package io.github.blairjin.user_order_api.dto.auth;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}