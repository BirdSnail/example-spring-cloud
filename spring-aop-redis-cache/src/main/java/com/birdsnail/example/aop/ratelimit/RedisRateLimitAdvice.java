package com.birdsnail.example.aop.ratelimit;

import com.birdsnail.example.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class RedisRateLimitAdvice {

    @Autowired
    private RedisService redisService;

    @Before("@annotation(RedisRateLimit)")
    public void aroundAdvice(JoinPoint joinPoint) throws Throwable {
        String string = joinPoint.toString();
//        System.out.println("joinPoint: " + string);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RedisRateLimit rateLimit = signature.getMethod().getAnnotation(RedisRateLimit.class);

        long seconds = System.currentTimeMillis() / 1000;
        String key = string + "_" + seconds;
        Long count = redisService.incrementAndGetByLua(key);
        System.out.println("redis value: " + count);
        if (count > rateLimit.limit()) {
            throw new RuntimeException("请求超过限流阈值");
        }
    }
}
