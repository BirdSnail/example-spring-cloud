package com.birdsnail.demo.easyexcel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.birdsnail.demo.easyexcel.model.ExcelSimpleModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * excel读取监听器，收集所有的数据行
 */
@Slf4j
public class ExcelCollectorReadListener implements ReadListener<ExcelSimpleModel> {

    private final List<ExcelSimpleModel> dataList = new ArrayList<>();

    @Override
    public void invoke(ExcelSimpleModel excelSimpleModel, AnalysisContext analysisContext) {
        dataList.add(excelSimpleModel);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("excel读取完成");
    }

    public List<ExcelSimpleModel> getDataList() {
        return dataList;
    }
}
