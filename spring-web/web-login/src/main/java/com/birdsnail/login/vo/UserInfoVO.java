package com.birdsnail.login.vo;

import com.birdsnail.login.dao.entity.ResourceEntity;
import com.birdsnail.login.dao.entity.RoleEntity;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoVO {

    private Long userId;

    private String username;

    private String password;

    private List<RoleEntity> roleList;

    private List<ResourceEntity> resourceList;

}
