package com.rbts.hrms.authentication.service;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenBlacklistService {

    private static RedisTemplate<String, Object> redisTemplate;
    private static final String TOKEN_PREFIX = "blacklisted-token:";

    public TokenBlacklistService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Adds Token to Redis Cache
     * @param token
     * @param expirationTime
     */
    public void addTokenToBlacklist(String token, long expirationTime) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.opsForValue().set(key, token, Duration.ofMillis(expirationTime));
    }

    /**
     * Checks whether the token is present in Redis Cache
     * @param token
     * @return
     */
    public static boolean isTokenBlacklisted(String token) {
        String key = TOKEN_PREFIX + token;
        return redisTemplate.hasKey(key);
    }
}
