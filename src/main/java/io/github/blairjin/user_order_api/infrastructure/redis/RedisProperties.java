package io.github.blairjin.user_order_api.infrastructure.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "redis")
public record RedisProperties(
        LoginFail loginFail,
        EmailVerification emailVerification,
        RefreshToken refreshToken
) {
    public record LoginFail(
            Duration ttl,
            int maxCount
    ) { }

    public record EmailVerification(
            Duration ttl
    ) { }

    public record RefreshToken(
            Duration ttl
    ) { }
}