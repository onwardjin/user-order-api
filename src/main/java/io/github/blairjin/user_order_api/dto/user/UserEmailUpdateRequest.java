package io.github.blairjin.user_order_api.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserEmailUpdateRequest(
        @NotBlank
        String email
) {
}