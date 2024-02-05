package com.birdsnail.login.controller;

import cn.hutool.jwt.JWT;
import com.birdsnail.login.service.UserService;
import com.birdsnail.login.vo.HttpResultResponse;
import com.birdsnail.login.vo.SignInReq;
import com.birdsnail.login.dao.entity.UserEntity;
import com.birdsnail.login.vo.UserInfoVO;
import com.birdsnail.login.vo.UserRegisterParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

import static com.birdsnail.login.vo.LogInConstant.JWT_SIGN_KEY;

@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String sayHello() {
        return "hello security";
    }

    @GetMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterParam userRegisterParam) {
        log.info("用户注册，param：{}", userRegisterParam);
        userService.registerUser(userRegisterParam);
        return ResponseEntity.ok("ok");
    }

    /**
     * 登录
     *
     * @param req 登录请求参数
     * @return jwt
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignInReq req) {
        log.info("login param:{}", req);
        UserEntity user = userService.findUserByName(req.getUsername());
        boolean matches = passwordEncoder.matches(req.getPassword(), user.getPassword());
        if (!matches) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码错误");
        }

        String jwt = JWT.create()
                .setPayload("username", req.getUsername())
                .setKey(JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8))
                .sign();
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/userInfo")
    public HttpResultResponse<UserInfoVO> userInfo(@RequestBody SignInReq req) {
        log.info("query userInfo param-->{}", req);
        UserInfoVO userInfoVO = userService.queryUser(req.getUsername());
        return HttpResultResponse.buildSuccess(userInfoVO);
    }

}
