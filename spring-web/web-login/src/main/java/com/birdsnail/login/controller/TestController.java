package com.birdsnail.login.controller;

import com.birdsnail.login.vo.HttpResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public HttpResultResponse<String> hello() {
        return HttpResultResponse.buildSuccess("test hello");
    }

}
