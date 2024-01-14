package com.birdsnail.example.aop.cache;

import com.birdsnail.example.aop.SpELParserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class RedisCacheAdvice {

    private Map<String, Object> cache = new HashMap<>();


    @Around("@annotation(redisCache)")
    public Object aroundAdvice(ProceedingJoinPoint pjp, RedisCache redisCache) throws Throwable {
//        System.out.println("before");
        Signature signature = pjp.getSignature();
        System.out.printf("当前正在被切面的方法：%s\n", signature.getName());
//        Object proxy = pjp.getThis();
//        System.out.println("当前代理对象name:" + proxy.getClass().getName());
//        Object[] args = pjp.getArgs();
//        System.out.println("aop拦截的方法参数：" + JSON.toJSONString(args));
//        Object target = pjp.getTarget();
//        System.out.println("原始对象：" + target.getClass().getName());
//
//        MethodSignature methodSignature = (MethodSignature) signature;
//        String[] parameterNames = methodSignature.getParameterNames();
//
//        System.out.println("连接点信息： " + pjp.toString());
        if (StringUtils.isAllBlank(redisCache.key(), redisCache.spelKey())) {
            throw new IllegalArgumentException(String.format("%s方法上注解@RedisCache的key()和spelKey()都为空", signature.getName()));
        }

        String cacheKey = redisCache.key();
        if (StringUtils.isBlank(cacheKey)) {
            cacheKey = SpELParserUtils.parse(pjp, redisCache.spelKey());
        }
        Object value = cache.get(cacheKey);
        if (value != null) {
            log.info("从缓存获取值成功");
            return value;
        }

        // 执行实际调用
        Object relRes = pjp.proceed();
        if (relRes != null) {
            cache.put(cacheKey, relRes);
        }
        return relRes;
    }
}
