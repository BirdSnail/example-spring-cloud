package com.birdsnail.test.configyaml;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

class YamlPropertySourceTest {


    @Test
    public void loadYaml() throws IOException {
        ClassPathResource cpResource = new ClassPathResource("yhd.yaml");
        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        List<PropertySource<?>> propertySources = yamlPropertySourceLoader.load("自定义的配置yhd.yaml, location-->classpath:/yhd.yaml", cpResource);
        propertySources.forEach(System.out::println);
        for (PropertySource<?> propertySource : propertySources) {
            System.out.println(propertySource.getProperty("name"));
        }
    }
}
