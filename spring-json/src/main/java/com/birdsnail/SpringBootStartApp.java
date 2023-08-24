package com.birdsnail;

import com.birdsnail.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootStartApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringBootStartApp.class);
        User user = ctx.getBean(User.class);
        System.out.println(user);
    }

}
