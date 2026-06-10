package io.github.blairjin.user_order_api.application.user.usecase;

import io.github.blairjin.user_order_api.application.user.command.UserCommandMapper;
import io.github.blairjin.user_order_api.application.user.command.UserCreateCommand;
import io.github.blairjin.user_order_api.application.user.command.UserProfileCreateCommand;
import io.github.blairjin.user_order_api.application.user.reader.UserReader;
import io.github.blairjin.user_order_api.application.user.service.UserCommandService;
import io.github.blairjin.user_order_api.domain.user.vo.EncodedPassword;
import io.github.blairjin.user_order_api.domain.user.vo.LoginId;
import io.github.blairjin.user_order_api.domain.user.vo.Password;
import io.github.blairjin.user_order_api.dto.user.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;
    private final UserCommandService userCommandService;

    public void execute(UserCreateRequest request){
        LoginId loginId = LoginId.of(request.loginId());
        userReader.validateDuplicateLoginId(loginId);

        Password rawPassword = Password.of(request.password());
        EncodedPassword password = encodePassword(rawPassword);

        UserCreateCommand userCreateCommand = UserCommandMapper.toCommand(loginId, password, request.email());
        UserProfileCreateCommand profileCommand = UserCommandMapper.toCommand(request);

        userCommandService.create(userCreateCommand, profileCommand);
    }

    private EncodedPassword encodePassword(Password rawPassword){
        String encodedPassword = passwordEncoder.encode(rawPassword.value());
        return EncodedPassword.of(encodedPassword);
    }
}