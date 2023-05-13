package com.bridsnail.demo.mybatisplus.vo;

import lombok.Data;

@Data
public class ActorQueryParam {

    private String actorId;

    private String firstName;

    private String movie;

    private int pageNum = 1;

    private int pageSize = 10;

}
