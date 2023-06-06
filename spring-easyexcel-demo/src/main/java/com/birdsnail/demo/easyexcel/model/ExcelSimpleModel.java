package com.birdsnail.demo.easyexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelSimpleModel {

    @ExcelProperty(value = "字段1")
    private String 字段1;

    @ExcelProperty(value = "字段2")
    private String 字段2;

    @ExcelProperty(value = "字段3")
    private String 字段3;

    @ExcelProperty(value = "字段4")
    private String 字段4;

    @ExcelProperty(value = "字段5")
    private String 字段5;

}
