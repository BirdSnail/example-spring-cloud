package com.birdsnail.example.service.ratelimit;

import com.birdsnail.example.aop.ratelimit.RedisRateLimit;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class RedisRateLimitExampleService {


    @RedisRateLimit(limit = 3)
    public void query() {
        System.out.println("执行了查询，返回：" + RandomStringUtils.randomPrint(10));
    }


}
