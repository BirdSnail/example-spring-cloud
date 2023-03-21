package com.birdsnail.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


@SpringBootApplication
public class StartSpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(StartSpringApplication.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        System.out.printf("yhd-->{name:%s}%n", environment.getProperty("yhd.name"));
    }
}
