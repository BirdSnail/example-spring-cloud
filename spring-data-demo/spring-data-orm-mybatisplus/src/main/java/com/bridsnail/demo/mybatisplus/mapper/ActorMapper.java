package com.bridsnail.demo.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bridsnail.demo.mybatisplus.entity.Actor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActorMapper extends BaseMapper<Actor> {
}