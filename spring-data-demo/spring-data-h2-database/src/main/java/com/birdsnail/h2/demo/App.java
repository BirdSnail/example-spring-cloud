package com.birdsnail.h2.demo;

import com.birdsnail.h2.demo.dao.UserRepository;
import com.birdsnail.h2.demo.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext app = SpringApplication.run(App.class);
        UserRepository userRepository = app.getBean(UserRepository.class);
        List<User> all = userRepository.findAll();
        System.out.println(all);

        User user = new User();
        user.setUserId(10);
        user.setUserName("yanghuadong");
        userRepository.save(user);
        System.out.println(userRepository.findById(10).get());
    }

}
