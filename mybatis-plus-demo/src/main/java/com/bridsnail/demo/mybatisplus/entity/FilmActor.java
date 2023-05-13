package com.bridsnail.demo.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName(value = "film_actor")
public class FilmActor {
    @TableId(value = "actor_id", type = IdType.INPUT)
    private Object actorId;

    @TableId(value = "film_id", type = IdType.INPUT)
    private Object filmId;

    @TableField(value = "last_update")
    private LocalDateTime lastUpdate;

    public static final String COL_ACTOR_ID = "actor_id";

    public static final String COL_FILM_ID = "film_id";

    public static final String COL_LAST_UPDATE = "last_update";
}