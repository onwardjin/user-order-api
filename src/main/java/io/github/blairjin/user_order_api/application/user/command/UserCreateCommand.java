package io.github.blairjin.user_order_api.application.user.command;

import io.github.blairjin.user_order_api.domain.user.vo.Email;
import io.github.blairjin.user_order_api.domain.user.vo.EncodedPassword;
import io.github.blairjin.user_order_api.domain.user.vo.LoginId;

public record UserCreateCommand(
        LoginId loginId,
        EncodedPassword password,
        Email email
) {
}