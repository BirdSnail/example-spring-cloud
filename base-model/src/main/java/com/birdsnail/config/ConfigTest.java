package com.birdsnail.config;

import com.birdsnail.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigTest {

    @Bean
    public User user() {
        User user = new User();
        user.setName("yanghuadong");
        user.setAge(100);
        return user;
    }

}
