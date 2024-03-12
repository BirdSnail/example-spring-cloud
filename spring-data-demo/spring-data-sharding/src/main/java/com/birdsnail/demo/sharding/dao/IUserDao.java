package com.birdsnail.demo.sharding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.birdsnail.demo.sharding.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends BaseMapper<User> {

    @Update("update t_user set password= #{password}, update_time = sysdate() where id =#{id}")
    int updatePassword(User user);

    @Select("select * from t_user where id = #{userId}")
    User findById2(Long userId);
}
