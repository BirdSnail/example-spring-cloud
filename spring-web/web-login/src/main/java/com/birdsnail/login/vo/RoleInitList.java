package com.birdsnail.login.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties
public class RoleInitList {

    private List<Role> roleList;

    @Data
    public static class Role{
        private String role;

        private String resource;
    }

}
