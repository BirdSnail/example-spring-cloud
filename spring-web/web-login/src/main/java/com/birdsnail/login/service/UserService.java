package com.birdsnail.login.service;

import com.birdsnail.login.dao.entity.UserEntity;
import com.birdsnail.login.vo.UserInfoVO;
import com.birdsnail.login.vo.UserRegisterParam;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * 注册用户
     */
    UserEntity registerUser(UserRegisterParam userRegisterParam);

    UserEntity findUserByName(String username);

    UserInfoVO queryUser(String username);

}
