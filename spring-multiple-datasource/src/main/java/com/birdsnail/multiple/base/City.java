package com.birdsnail.multiple.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "city")
public class City {
    @TableId(value = "ID", type = IdType.INPUT)
    private Integer id;

    @TableField(value = "`Name`")
    private String name;

    @TableField(value = "CountryCode")
    private String countrycode;

    @TableField(value = "District")
    private String district;

    @TableField(value = "Population")
    private Integer population;
}