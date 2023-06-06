package com.birdsnail.demo.easyexcel.controller;

import com.birdsnail.demo.easyexcel.model.ExcelSimpleModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SpringTestControllerTest {

    @Autowired
    private SpringTestController springTestController;

    @Test
    void fillExcel() throws IOException {
        List<ExcelSimpleModel> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(create(i));
        }
        springTestController.fillExcel(data);
    }

    private ExcelSimpleModel create(int num) {
        ExcelSimpleModel model1 = new ExcelSimpleModel();
        model1.set字段1(String.valueOf(num));
        model1.set字段2("1321");
        model1.set字段3(RandomStringUtils.randomAlphabetic(10));
        model1.set字段4("fasf");
        model1.set字段4(String.valueOf(RandomUtils.nextInt(0, 500)));
        return model1;
    }

}