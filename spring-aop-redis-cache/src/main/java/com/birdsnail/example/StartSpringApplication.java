package com.birdsnail.example;

import com.birdsnail.example.service.SimpleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class StartSpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(StartSpringApplication.class, args);
        SimpleService simpleService = applicationContext.getBean(SimpleService.class);
//        String name = simpleService.addPrefix("兰博");
//        System.out.println(name);

//        simpleService.addPrefix("兰博");
//        simpleService.addPrefix("兰博");
//        simpleService.addPrefix("兰博");
        System.out.println(simpleService.addPrefix(null));

    }
}
