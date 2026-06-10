package io.github.blairjin.user_order_api.application.auth.service;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidTokenException;
import io.github.blairjin.user_order_api.infrastructure.redis.RedisKeys;
import io.github.blairjin.user_order_api.infrastructure.redis.RedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisProperties redisProperties;

    public void save(Long userId, String refreshToken){
        String key = RedisKeys.refreshToken(userId);
        Duration ttl = redisProperties.refreshToken().ttl();

        redisTemplate.opsForValue().set(key, refreshToken, ttl);
    }

    public void validateRefreshToken(Long userId, String refreshToken){
        String key = RedisKeys.refreshToken(userId);
        String result = redisTemplate.opsForValue().get(key);

        if(!refreshToken.equals(result)){
            throw new InvalidTokenException();
        }
    }

    public void delete(Long userId){
        String key = RedisKeys.refreshToken(userId);
        redisTemplate.delete(key);
    }
}