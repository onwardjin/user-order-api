package io.github.blairjin.user_order_api.application.auth.usecase;

import io.github.blairjin.user_order_api.application.auth.service.AuthenticationService;
import io.github.blairjin.user_order_api.application.auth.service.RefreshTokenService;
import io.github.blairjin.user_order_api.domain.user.User;
import io.github.blairjin.user_order_api.domain.user.vo.LoginId;
import io.github.blairjin.user_order_api.domain.user.vo.Password;
import io.github.blairjin.user_order_api.dto.auth.LoginRequest;
import io.github.blairjin.user_order_api.dto.auth.LoginResponse;
import io.github.blairjin.user_order_api.infrastructure.jwt.JwtProvider;
import io.github.blairjin.user_order_api.infrastructure.jwt.JwtUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCase {
    private final JwtProvider jwtProvider;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse execute(LoginRequest request){
        LoginId loginId = LoginId.of(request.loginId());
        Password password = Password.of(request.password());

        User user = authenticationService.authenticate(loginId, password);
        JwtUserInfo userInfo = new JwtUserInfo(user.getId(), user.getLoginId(), user.getRole());

        String accessToken = jwtProvider.createAccessToken(userInfo);

        String refreshToken = jwtProvider.createRefreshToken(user.getId());
        refreshTokenService.save(user.getId(), refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }
}