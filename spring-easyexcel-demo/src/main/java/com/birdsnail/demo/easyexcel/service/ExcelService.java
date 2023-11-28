package com.birdsnail.demo.easyexcel.service;

import com.alibaba.excel.EasyExcel;

import java.util.List;

public class ExcelService {

    /**
     * 简单读，一次性读取所有
     *
     * @param path excel路径
     */
    public static void readAll(String path) {
        List<Object> objects = EasyExcel.read(path)
                .headRowNumber(1)
                .doReadAllSync();
        for (Object object : objects) {
            System.out.println(object);
        }
    }

    public static void main(String[] args) {
        readAll("");
    }


}
