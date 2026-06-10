package io.github.blairjin.user_order_api.application.auth.usecase;

import io.github.blairjin.user_order_api.application.auth.service.RefreshTokenService;
import io.github.blairjin.user_order_api.application.user.reader.UserReader;
import io.github.blairjin.user_order_api.domain.user.User;
import io.github.blairjin.user_order_api.dto.auth.AccessTokenResponse;
import io.github.blairjin.user_order_api.dto.auth.TokenRefreshRequest;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidTokenException;
import io.github.blairjin.user_order_api.infrastructure.jwt.JwtProvider;
import io.github.blairjin.user_order_api.infrastructure.jwt.JwtUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshAccessTokenUseCase {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserReader userReader;

    public AccessTokenResponse execute(TokenRefreshRequest request){
        String refreshToken = request.refreshToken();

        if(!jwtProvider.validateToken(refreshToken)){
            throw new InvalidTokenException();
        }

        Long userId = jwtProvider.getUserId(refreshToken);
        refreshTokenService.validateRefreshToken(userId, refreshToken);

        User user = userReader.getUserById(userId);

        JwtUserInfo userInfo = JwtUserInfo.from(user);
        String accessToken = jwtProvider.createAccessToken(userInfo);

        return new AccessTokenResponse(accessToken);
    }
}