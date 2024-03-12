package com.birdsnail.demo.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user")
public class User {

    @TableId
    private Long id;

    private String userName;

    private String password;
    private String phoneNumber;
    private String description;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
