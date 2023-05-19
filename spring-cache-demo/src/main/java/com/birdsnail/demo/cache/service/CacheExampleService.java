package com.birdsnail.demo.cache.service;

import com.birdsnail.demo.cache.pojo.User;
import com.birdsnail.demo.cache.pojo.UserParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
// 类级别注解，允许@Cacheable共享配置的属性，可以覆盖
@CacheConfig(cacheNames = "redis-cache")
public class CacheExampleService {

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Cacheable(key = "#param.id")
    public User getUser(UserParam param) {
        log.info("获取user param:{}", param);
        User user = new User();
        user.setId(atomicInteger.incrementAndGet());
        user.setName("yhd-" + atomicInteger.get());
        user.setAge(28);
        return user;
    }

    @Cacheable("redis-cache2")
    public User findUserByName(String name) {
        log.info("获取user by Name, param:{}", name);
        User user = new User();
        user.setId(1000);
        user.setName(name);
        user.setAge(28);
        return user;
    }


}
