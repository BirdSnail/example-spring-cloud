package com.birdsnail.demo.cache;

import com.alibaba.fastjson2.JSON;
import com.birdsnail.demo.cache.pojo.User;
import com.birdsnail.demo.cache.pojo.UserParam;
import com.birdsnail.demo.cache.service.CacheExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
@EnableCaching
@Slf4j
public class SpringCacheDemoStartApplication implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CacheExampleService cacheExampleService;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringCacheDemoStartApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserParam userParam = new UserParam();
        for (int i = 0; i < 2; i++) {
            userParam.setId(i);
            User user = cacheExampleService.getUser(userParam);
            System.out.println(JSON.toJSONString(user));
        }

        for (int i = 0; i < 2; i++) {
            userParam.setId(i);
            User user = cacheExampleService.getUser(userParam);
            System.out.println(JSON.toJSONString(user));
        }

        cacheExampleService.findUserByName("yhd");
        cacheExampleService.findUserByName("heihaha");
        cacheExampleService.findUserByName("yhd");
        cacheExampleService.findUserByName("heihaha");

        System.out.println(cacheExampleService.findAllUserByName("yhd"));
        System.out.println(cacheExampleService.findAllUserByName("yhd"));
    }
}
