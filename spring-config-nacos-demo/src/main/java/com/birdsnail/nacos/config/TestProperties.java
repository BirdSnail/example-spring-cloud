package com.birdsnail.nacos.config;

import com.birdsnail.model.User;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "test")
@RefreshScope // 支持配置值动态刷新
@Data
public class TestProperties {

    private Dog dog;

    private User user;

    @Data
    public static class Dog{

        String name;

        String color;

    }

}
