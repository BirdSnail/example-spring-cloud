package com.birdsnail.example.service;

import com.alibaba.fastjson2.JSON;
import com.birdsnail.example.aop.cache.RedisCache;
import com.birdsnail.example.entity.Car;
import com.birdsnail.example.entity.User;
import org.springframework.stereotype.Service;

@Service
public class SimpleService {


    @RedisCache(spelKey = "#name")
    public String addPrefix(String name) {
        return "<yhd>" + name;
    }

    @RedisCache(spelKey = "#user.name")
    public String updateUser(User user, Car car) {
        user.setCar(car);
        return JSON.toJSONString(user);
    }

}
