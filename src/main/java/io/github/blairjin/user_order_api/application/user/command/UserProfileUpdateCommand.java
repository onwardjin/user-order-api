package io.github.blairjin.user_order_api.application.user.command;

import io.github.blairjin.user_order_api.domain.user.vo.BirthDate;
import io.github.blairjin.user_order_api.domain.user.vo.PhoneNumber;
import io.github.blairjin.user_order_api.domain.user.vo.UserName;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

public record UserProfileUpdateCommand(
        JsonNullable<UserName> userName,
        JsonNullable<String> firstName,
        JsonNullable<String> lastName,
        JsonNullable<BirthDate> birthDate,
        JsonNullable<PhoneNumber> phoneNumber
) {
}