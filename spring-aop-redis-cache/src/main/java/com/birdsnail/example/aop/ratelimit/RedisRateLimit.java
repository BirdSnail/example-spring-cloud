package com.birdsnail.example.aop.ratelimit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * redis限流注解
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisRateLimit {

    /**
     * 限流阈值
     */
    int limit();
}
