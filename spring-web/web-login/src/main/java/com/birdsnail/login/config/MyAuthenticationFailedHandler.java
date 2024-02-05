package com.birdsnail.login.config;

import com.birdsnail.login.util.JsonUtil;
import com.birdsnail.login.vo.HttpResultResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationFailedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        HttpResultResponse<String> body = HttpResultResponse.buildError("用户认证失败");
        response.getWriter().println(JsonUtil.toJsonStr(body));
        response.getWriter().flush();
    }

}