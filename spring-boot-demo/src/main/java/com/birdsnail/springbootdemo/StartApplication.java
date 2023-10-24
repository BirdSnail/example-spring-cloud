package com.birdsnail.springbootdemo;

import com.birdsnail.springbootdemo.annotation.SpringAnnotationDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(StartApplication.class);
        SpringAnnotationDemo annotationDemo = run.getBean(SpringAnnotationDemo.class);
        annotationDemo.printYhdJobMethod();
    }

}
