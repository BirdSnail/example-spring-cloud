package com.birdsnail.nacos.controller;

import com.alibaba.fastjson2.JSON;
import com.birdsnail.nacos.config.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoController {

    @Autowired
    private TestProperties testProperties;

    @GetMapping("/value")
    public String testValue() {
        return JSON.toJSONString(testProperties);
    }

}
