package io.github.blairjin.user_order_api.application.user.command;

import io.github.blairjin.user_order_api.domain.user.vo.BirthDate;
import io.github.blairjin.user_order_api.domain.user.vo.PhoneNumber;
import io.github.blairjin.user_order_api.domain.user.vo.UserName;

public record UserProfileCreateCommand(
        UserName userName,
        String firstName,
        String lastName,
        BirthDate birthDate,
        PhoneNumber phoneNumber
) {
}