package com.bridsnail.demo.mybatisplus.service;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bridsnail.demo.mybatisplus.entity.LookupItem;
import com.bridsnail.demo.mybatisplus.mapper.LookupItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DictionaryService {

    @Autowired
    private LookupItemMapper lookupItemMapper;


    public void updateEconKind() {
        List<Map<Integer, String>> objects = EasyExcel.read("E:\\java-project\\example-spring-cloud\\mybatis-plus-demo\\src\\main\\resources\\企业类型.xlsx")
                .sheet(0)
                .headRowNumber(1)
                .doReadSync();

        List<String> sqls = new ArrayList<>();
        for (Map<Integer, String> object : objects) {
            String type = object.get(0);
            String yx = object.get(1); // 有限责任公司
            String gf = object.get(2); // 股份有限公司
            String gq = object.get(3); // 国有企业
            String sy = object.get(4);
            String dz = object.get(5);  // 独资
            String gat = object.get(6); // 港澳台
            String wstz = object.get(7); // 外商投资企业
            String other = object.get(8); // 其他
            String fgs = object.get(9); // 分公司

            String value = Stream.of(yx, gf, gq, sy, dz, gat, wstz, other, fgs)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(","));

            LambdaQueryWrapper<LookupItem> lqw = new LambdaQueryWrapper<>();
            lqw.eq(LookupItem::getClassifyCode, "ENTERPRICE_OPTIONS_ENTER_TYPE")
                    .eq(LookupItem::getItemName, type)
                    .eq(LookupItem::getStatus, 0);
            LookupItem et = lookupItemMapper.selectOne(lqw);
            if (et != null) {
                if (StringUtils.isNotBlank(value)) {
                    LookupItem update = new LookupItem();
                    update.setId(et.getId());
                    update.setItemAttr2(value);
//                    lookupItemMapper.updateById(update);
//                    System.out.println("更新成功:" + type + ", itemAttr2:" + value);

                    String sql = String.format("update crm_lookup_item set item_attr2='%s' where classify_code='ENTERPRICE_OPTIONS_ENTER_TYPE' and item_code='%s';",
                            value, et.getItemCode());
                    sqls.add(sql);
                }
            }else {
                System.out.println(type + "--找不到");
            }
        }

        System.out.println("===============================");
        for (String sql : sqls) {
            System.out.println(sql);
        }
    }

    public static void main(String[] args) {
        List<Map<Integer, String>> objects = EasyExcel.read("E:\\java-project\\example-spring-cloud\\mybatis-plus-demo\\src\\main\\resources\\企业类型.xlsx")
                .sheet(0)
                .headRowNumber(1)
                .doReadSync();
        for (Map<Integer, String> object : objects) {
            System.out.println(object);
        }
    }



}
