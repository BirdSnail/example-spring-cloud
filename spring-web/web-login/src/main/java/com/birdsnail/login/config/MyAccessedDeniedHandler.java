package com.birdsnail.login.config;

import com.birdsnail.login.util.JsonUtil;
import com.birdsnail.login.vo.HttpResultResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAccessedDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        HttpResultResponse<String> body = HttpResultResponse.buildError("无权限！");
        response.getWriter().println(JsonUtil.toJsonStr(body));
        response.getWriter().flush();
    }

}
