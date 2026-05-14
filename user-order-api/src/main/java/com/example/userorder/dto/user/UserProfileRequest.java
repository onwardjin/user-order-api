package com.example.userorder.dto.user;

import java.time.LocalDate;

public record UserProfileRequest(
        String name,
        LocalDate birthDate,
        String email
) {
}