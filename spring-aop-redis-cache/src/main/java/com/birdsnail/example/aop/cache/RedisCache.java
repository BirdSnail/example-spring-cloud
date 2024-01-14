package com.birdsnail.example.aop.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    /**
     * 缓存key,如果设置了{@code key}和{@link  RedisCache#spelKey()}，优先使用这个key
     */
    String key() default "";

    /**
     * 使用spel表达式进行评估来获取key。为了方便实现，暂不支持spel表达式模板
     */
    String spelKey() default "";

}
