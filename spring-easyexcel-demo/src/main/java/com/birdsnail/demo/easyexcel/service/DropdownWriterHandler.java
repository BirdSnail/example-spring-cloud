package com.birdsnail.demo.easyexcel.service;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;

/**
 * EasyExcel设置下拉项
 */
public class DropdownWriterHandler implements SheetWriteHandler {

    public static final String DICT_SHEET_NAME = "dictSheet";

    private final int firstRow;
    private final int lastRow;
    private final int firstCol;
    private final int lastCol;
    private final String[] data;

    public DropdownWriterHandler(int firstRow, int lastRow, int firstCol, int lastCol, List<String> data) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
        if (data == null) {
            throw new IllegalArgumentException("下拉数据不能为空");
        }
        this.data = data.toArray(new String[0]);
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 区间设置
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);

        if (data.length <= 50) {
            // 下拉内容
            DataValidationConstraint constraint = helper.createExplicitListConstraint(data);
            DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
            sheet.addValidationData(dataValidation);
        }
        // 下拉项数据超过50的时候，将数据保存在一个单独的sheet中，使用引用公示设置下拉项
        else {
            Workbook workbook = writeWorkbookHolder.getWorkbook();
            Sheet dictSheet = createDictSheet(workbook, 0);

            // 下拉框数据来源 eg:Sheet2!$C$2:$C$11
            String refers = String.format("%s!$A$1:$A$%d", dictSheet.getSheetName(), data.length + 1);
            // 创建可被其他单元格引用的名称
            Name name = workbook.createName();
            String referName = "dictRefer";
            name.setNameName(referName);
            name.setRefersToFormula(refers);  // 设置公式

            DataValidationConstraint constraint = helper.createFormulaListConstraint(referName);
            DataValidation validation = helper.createValidation(constraint, cellRangeAddressList);
            sheet.addValidationData(validation);
        }

    }

    // 创建数据字典sheet页
    private Sheet createDictSheet(Workbook workbook, int colIndex) {
        Sheet dictSheet = workbook.createSheet(DICT_SHEET_NAME);
        int index = workbook.getSheetIndex(DICT_SHEET_NAME);
        workbook.setSheetHidden(index, true); // 隐藏sheet
        // 设置字典值
        for (int i = 0; i < data.length; i++) {
            Row row = dictSheet.getRow(i);
            if (row == null) {
                row = dictSheet.createRow(i);
            }
            row.createCell(colIndex).setCellValue(data[i]);
        }
        return dictSheet;
    }
}
