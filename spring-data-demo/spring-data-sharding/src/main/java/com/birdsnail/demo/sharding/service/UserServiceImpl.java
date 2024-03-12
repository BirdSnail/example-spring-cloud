package com.birdsnail.demo.sharding.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.birdsnail.demo.sharding.dao.IUserDao;
import com.birdsnail.demo.sharding.entity.User;
import com.birdsnail.demo.sharding.entity.UserQueryBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;


    @Override
    public List<User> findList(UserQueryBean userQueryBean) {
        LambdaQueryWrapper<User> lqw = new QueryWrapper<User>().lambda();
        if (StringUtils.isNotBlank(userQueryBean.getUserName())) {
            lqw.like(User::getUserName, userQueryBean.getUserName());
        }
        if (StringUtils.isNotBlank(userQueryBean.getDescription())) {
            lqw.like(User::getDescription, userQueryBean.getDescription());
        }
        if (StringUtils.isNotBlank(userQueryBean.getPhoneNumber())) {
            lqw.like(User::getPhoneNumber, userQueryBean.getPhoneNumber());
        }
        return userDao.selectList(lqw);
    }

    @Override
    public User findById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public int deleteById(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public int update(User user) {
        return userDao.updateById(user);
    }

    @Override
    public int save(User user) {
        user.setUpdateTime(LocalDateTime.now());
        return userDao.insert(user);
    }

    @Override
    public int updatePassword(User user) {
        return userDao.updatePassword(user);
    }

    @Override
    public User findById2(Long userId) {
        return userDao.findById2(userId);
    }
}
