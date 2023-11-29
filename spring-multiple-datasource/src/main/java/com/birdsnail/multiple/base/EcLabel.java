package com.birdsnail.multiple.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
    * 标签管理
    */
@Data
@TableName(value = "ec_label")
public class EcLabel {
    @TableId(value = "label_id", type = IdType.INPUT)
    private Long labelId;

    /**
     * 标签名称
     */
    @TableField(value = "label_name")
    private String labelName;

    /**
     * 标签组ID
     */
    @TableField(value = "label_group_id")
    private Long labelGroupId;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "creation_date")
    private LocalDateTime creationDate;

    /**
     * 最后更新人
     */
    @TableField(value = "last_updated_by")
    private Long lastUpdatedBy;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_date")
    private LocalDateTime lastUpdateDate;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 是否开启:1 是 2否  
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 删除状态 0:正常，1：已删除
     */
    @TableField(value = "delete_status")
    private Integer deleteStatus;
}