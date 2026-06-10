package io.github.blairjin.user_order_api.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String loginId,

        @NotBlank
        String password
) {
}