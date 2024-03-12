package com.birdsnail.configurationprocessor.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "demo.processor")
public class DemoProperties {

    /**
     * 姓名
     */
    private String name;

    /**
     * 中文名称
     */
    private String nameCn;

    /**
     * 英文名称
     */
    private String nameEn;

    /**
     * 联系号码
     */
    private String[] hobbies;

    /**
     * 性别
     */
    private SexEnum sexEnum;

    /**
     *
     */
    private boolean single;

    /**
     * 毕业学校
     */
    @NestedConfigurationProperty
    private School school;

    /**
     * 所在城市
     */
    private City city;

    enum SexEnum {
        MAN, WOMAN
    }

    @Data
    static class City {
        private String no;
        private String name;
    }
}
