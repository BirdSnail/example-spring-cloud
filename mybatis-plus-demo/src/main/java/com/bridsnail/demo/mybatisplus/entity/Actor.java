package com.bridsnail.demo.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName(value = "actor")
public class Actor {
    @TableId(value = "actor_id", type = IdType.INPUT)
    private Object actorId;

    @TableField(value = "first_name")
    private String firstName;

    @TableField(value = "last_name")
    private String lastName;

    @TableField(value = "last_update")
    private LocalDateTime lastUpdate;

    public static final String COL_ACTOR_ID = "actor_id";

    public static final String COL_FIRST_NAME = "first_name";

    public static final String COL_LAST_NAME = "last_name";

    public static final String COL_LAST_UPDATE = "last_update";
}