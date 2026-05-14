package com.example.userorder.dto.common;

public record ErrorResponse(
        int status,
        String message
) {
}