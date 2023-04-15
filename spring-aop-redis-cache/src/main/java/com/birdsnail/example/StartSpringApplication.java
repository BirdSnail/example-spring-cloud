package com.birdsnail.example;

import com.birdsnail.example.entity.Car;
import com.birdsnail.example.entity.User;
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
//        System.out.println(simpleService.addPrefix(null));
        User user = new User();
        user.setName("bird");
        Car car = new Car();
        car.setModel("BYD");
        System.out.println(simpleService.updateUser(user, car));
        System.out.println(simpleService.updateUser(user, car));

    }
}
