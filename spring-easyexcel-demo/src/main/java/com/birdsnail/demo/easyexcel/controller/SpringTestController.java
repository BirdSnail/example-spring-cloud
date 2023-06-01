package com.birdsnail.demo.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.birdsnail.demo.easyexcel.service.DropdownWriterHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

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
        String fileName = URLEncoder.encode("导入模板", "UTF-8").replaceAll("\\+", "%20");
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

}
