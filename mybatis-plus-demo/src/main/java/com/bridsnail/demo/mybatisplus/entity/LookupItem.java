package com.bridsnail.demo.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
    * 数据字典表
    */
@Data
@TableName(value = "crm_lookup_item")
public class LookupItem {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 字典类型编码
     */
    @TableField(value = "classify_code")
    private String classifyCode;

    /**
     * 字典编码
     */
    @TableField(value = "item_code")
    private String itemCode;

    /**
     * 编码名称
     */
    @TableField(value = "item_name")
    private String itemName;

    /**
     * 字典序号
     */
    @TableField(value = "item_index")
    private String itemIndex;

    /**
     * 扩展字段1
     */
    @TableField(value = "item_attr1")
    private Object itemAttr1;

    /**
     * 扩展字段2
     */
    @TableField(value = "item_attr2")
    private String itemAttr2;

    /**
     * 扩展字段3
     */
    @TableField(value = "item_attr3")
    private Object itemAttr3;

    /**
     * 状态,0有效，1无效
     */
    @TableField(value = "`status`")
    private Object status;

    /**
     * 父级字段编码
     */
    @TableField(value = "item_parent_code")
    private String itemParentCode;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Integer createdBy;

    /**
     * 创建人名称
     */
    @TableField(value = "created_name")
    private String createdName;

    /**
     *  创建时间 
     */
    @TableField(value = "creation_date")
    private LocalDateTime creationDate;

    /**
     * 更新人
     */
    @TableField(value = "last_updated_by")
    private Integer lastUpdatedBy;

    /**
     * 更新人名称
     */
    @TableField(value = "last_updated_name")
    private String lastUpdatedName;

    /**
     *  最后更新时间 
     */
    @TableField(value = "last_update_date")
    private LocalDateTime lastUpdateDate;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;
}