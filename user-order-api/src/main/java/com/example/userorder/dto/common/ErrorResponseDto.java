package com.example.userorder.dto.common;

public record ErrorResponseDto(
        int status,
        String message
) {
}