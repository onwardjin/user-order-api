package io.github.blairjin.user_order_api.application.auth.service;

import io.github.blairjin.user_order_api.domain.user.vo.LoginId;
import io.github.blairjin.user_order_api.exception.LOCKED.AccountLockedException;
import io.github.blairjin.user_order_api.infrastructure.redis.RedisKeys;
import io.github.blairjin.user_order_api.infrastructure.redis.RedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginFailService {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisProperties redisProperties;

    public void increase(LoginId loginId){
        String key = RedisKeys.loginFail(loginId);
        redisTemplate.opsForValue().increment(key);

        redisTemplate.expire(key, redisProperties.loginFail().ttl());
    }

    public void reset(LoginId loginId){
        String key = RedisKeys.loginFail(loginId);
        redisTemplate.delete(key);
    }

    public void validateNotLocked(LoginId loginId){
        String key = RedisKeys.loginFail(loginId);

        String result = redisTemplate.opsForValue().get(key);

        if(result==null){
            return;
        }

        long count = Long.parseLong(result);

        if(count >= redisProperties.loginFail().maxCount()){
            throw new AccountLockedException();
        }
    }
}