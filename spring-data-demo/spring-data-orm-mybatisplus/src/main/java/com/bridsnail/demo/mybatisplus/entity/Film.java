package com.bridsnail.demo.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName(value = "film")
public class Film {
    @TableId(value = "film_id", type = IdType.INPUT)
    private Object filmId;

    @TableField(value = "title")
    private String title;

    @TableField(value = "description")
    private String description;

    @TableField(value = "release_year")
    private Object releaseYear;

    @TableField(value = "language_id")
    private Object languageId;

    @TableField(value = "original_language_id")
    private Object originalLanguageId;

    @TableField(value = "rental_duration")
    private Object rentalDuration;

    @TableField(value = "rental_rate")
    private Double rentalRate;

    @TableField(value = "`length`")
    private Object length;

    @TableField(value = "replacement_cost")
    private Double replacementCost;

    @TableField(value = "rating")
    private Object rating;

    @TableField(value = "special_features")
    private Object specialFeatures;

    @TableField(value = "last_update")
    private LocalDateTime lastUpdate;

    public static final String COL_FILM_ID = "film_id";

    public static final String COL_TITLE = "title";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_RELEASE_YEAR = "release_year";

    public static final String COL_LANGUAGE_ID = "language_id";

    public static final String COL_ORIGINAL_LANGUAGE_ID = "original_language_id";

    public static final String COL_RENTAL_DURATION = "rental_duration";

    public static final String COL_RENTAL_RATE = "rental_rate";

    public static final String COL_LENGTH = "length";

    public static final String COL_REPLACEMENT_COST = "replacement_cost";

    public static final String COL_RATING = "rating";

    public static final String COL_SPECIAL_FEATURES = "special_features";

    public static final String COL_LAST_UPDATE = "last_update";
}