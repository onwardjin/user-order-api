package io.github.blairjin.user_order_api.application.auth.service;

import io.github.blairjin.user_order_api.application.user.reader.UserReader;
import io.github.blairjin.user_order_api.domain.user.User;
import io.github.blairjin.user_order_api.domain.user.vo.LoginId;
import io.github.blairjin.user_order_api.domain.user.vo.Password;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidLoginException;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;
    private final LoginFailService loginFailService;

    public User authenticate(LoginId loginId, Password password) {
        loginFailService.validateNotLocked(loginId);

        User user;

        try {
            user = userReader.getUserByLoginId(loginId);
        } catch (UserNotFoundException e) {
            loginFailService.increase(loginId);
            throw new InvalidLoginException();
        }

        if (!passwordEncoder.matches(password.value(), user.getEncodedPassword())) {
            loginFailService.increase(loginId);
            throw new InvalidLoginException();
        }

        loginFailService.reset(loginId);
        return user;
    }
}
