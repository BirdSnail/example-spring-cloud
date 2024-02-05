package com.birdsnail.login.vo;

import lombok.Data;

/**
 * 登录请求参数
 */
@Data
public class SignInReq {

    private String username;

    private String password;
}
