package com.birdsnail.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RedisService {

    public static final DefaultRedisScript<Long> RATE_LIMIT_LUA;

    static {
        RATE_LIMIT_LUA = new DefaultRedisScript<>();
        ResourceScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("lua/rateLimit.lua"));
        RATE_LIMIT_LUA.setScriptSource(scriptSource);
        RATE_LIMIT_LUA.setResultType(Long.class);
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Long incrementAndGetByLua(String key) {
        // 执行 lua 脚本
        return stringRedisTemplate.execute(RATE_LIMIT_LUA, Collections.singletonList(key), "3");
    }


}
