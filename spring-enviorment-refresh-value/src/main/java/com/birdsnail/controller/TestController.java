package com.birdsnail.controller;

import com.birdsnail.env.NotLogin;
import com.birdsnail.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yhd/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/name")
    @NotLogin(path = "/yhd/test/name")
    public String test() {
        return testService.getName();
    }

}
