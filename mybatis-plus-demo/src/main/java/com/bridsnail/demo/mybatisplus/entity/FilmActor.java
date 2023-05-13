package com.bridsnail.demo.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "film_actor")
public class FilmActor {
    @TableField(value = "actor_id")
    private Integer actorId;

    @TableField(value = "film_id")
    private Integer filmId;

    @TableField(value = "last_update")
    private LocalDateTime lastUpdate;

    public static final String COL_ACTOR_ID = "actor_id";

    public static final String COL_FILM_ID = "film_id";

    public static final String COL_LAST_UPDATE = "last_update";
}