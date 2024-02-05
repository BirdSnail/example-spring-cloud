package com.birdsnail.login.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties
public class UserInitList {

    private List<User> user;

    @Data
    public static class User {
        private String name;

        private String password;

        private List<String> roles;
    }
}
