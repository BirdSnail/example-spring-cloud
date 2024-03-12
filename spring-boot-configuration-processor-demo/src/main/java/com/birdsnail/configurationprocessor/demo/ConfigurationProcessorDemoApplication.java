package com.birdsnail.configurationprocessor.demo;

import com.birdsnail.configurationprocessor.demo.config.DemoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DemoProperties.class)
public class ConfigurationProcessorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationProcessorDemoApplication.class);
    }
}
