package com.birdsnail.demo.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.birdsnail.demo.easyexcel.model.ExcelSimpleModel;
import com.birdsnail.demo.easyexcel.service.DropdownWriterHandler;
import com.birdsnail.demo.easyexcel.service.ExcelCollectorReadListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * excel三大操作：
 * <ol>
 *     <li>读取</li>
 *     <li>写入</li>
 *     <li>填充</li>
 */
@RestController
@RequestMapping("/birdsnail/test/")
public class SpringTestController {


    /**
     * 下载导入模板
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        File template = ResourceUtils.getFile("classpath:template/importTemplate.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("导入模板", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream())
                .withTemplate(template)
                .sheet(0)
                .registerWriteHandler(getDropdownWriterHandler())
                .doWrite(Collections.emptyList());
    }

    // 获取下拉项处理器
    private DropdownWriterHandler getDropdownWriterHandler() {
        List<String> data = List.of("hello", "world", "你好", "世界");
        return new DropdownWriterHandler(2, 13, 0, 0, data);
    }

    /**
     * 上传文件
     */
    @GetMapping("/upload")
    public void upload(MultipartFile file) throws IOException {
        ExcelCollectorReadListener readListener = new ExcelCollectorReadListener();
        EasyExcel.read(file.getInputStream(), ExcelSimpleModel.class, readListener)
                .sheet().headRowNumber(2) // 指定表头所在的行
                .doRead();

        List<ExcelSimpleModel> dataList = readListener.getDataList();
        System.out.println("读取到excel数据行数：" + dataList.size());
    }

    /**
     * 填充excel
     */
    @PostMapping("/upload")
    public void fillExcel(@RequestBody List<ExcelSimpleModel> dataList) throws IOException {
        File template = ResourceUtils.getFile("classpath:template/fillTemplate.xlsx");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream).withTemplate(template).sheet().doFill(dataList);  // 列表填充

        byte[] byteArray = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        ExcelCollectorReadListener readListener = new ExcelCollectorReadListener();
        EasyExcel.read(inputStream, ExcelSimpleModel.class, readListener)
                .sheet().headRowNumber(2) // 指定表头所在的行
                .doRead();
        for (ExcelSimpleModel model : readListener.getDataList()) {
            System.out.println(JSON.toJSONString(model));
        }
    }

}
