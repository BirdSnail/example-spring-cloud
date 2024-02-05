package com.birdsnail.login.vo;

import lombok.Data;

@Data
public class HttpResultResponse<T> {

    /**
     * 响应成功 or 失败
     */
    private Boolean success;

    /**
     * 信息提示
     */
    private String message;

    /**
     * http响应的body
     */
    private T body;

    public static <T> HttpResultResponse<T> buildSuccess(T body) {
        return buildSuccess(body, "响应成功");
    }

    public static <T> HttpResultResponse<T> buildSuccess(T body, String message) {
        HttpResultResponse<T> response = new HttpResultResponse<>();
        response.success = true;
        response.message = message;
        response.body = body;
        return response;
    }

    public static <T> HttpResultResponse<T> buildError() {
        return buildError("响应失败");
    }

    public static <T> HttpResultResponse<T> buildError(String message) {
        HttpResultResponse<T> response = new HttpResultResponse<>();
        response.success = false;
        response.message = message;
        response.body = null;
        return response;
    }
}