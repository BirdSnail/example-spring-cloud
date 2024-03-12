package com.birdsnail.demo.sharding.service;

import com.birdsnail.demo.sharding.entity.User;
import com.birdsnail.demo.sharding.entity.UserQueryBean;

import java.util.List;

public interface IUserService {

    List<User> findList(UserQueryBean userQueryBean);

    User findById(Long id);

    int deleteById(Long id);

    int update(User user);

    int save(User user);

    int updatePassword(User user);

    User findById2(Long userId);
}
