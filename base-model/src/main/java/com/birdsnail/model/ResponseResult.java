package com.birdsnail.model;

import lombok.Data;

@Data
public class ResponseResult<T> {

    private int code;

    private String msg;

    private T data;

    public static <R> ResponseResult<R> success(R data) {
        ResponseResult<R> result = new ResponseResult<>();
        result.setCode(200);
        result.setData(data);
        return result;
    }
}
