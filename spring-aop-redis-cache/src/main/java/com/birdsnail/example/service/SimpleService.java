package com.birdsnail.example.service;

import com.birdsnail.example.aop.RedisCache;
import org.springframework.stereotype.Service;

@Service
public class SimpleService {


    @RedisCache(spelKey = "#name")
    public String addPrefix(String name) {
        return "<yhd>" + name;
    }
}
