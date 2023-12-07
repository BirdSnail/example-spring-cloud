package com.bridsnail.demo.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bridsnail.demo.mybatisplus.entity.Film;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FilmMapper extends BaseMapper<Film> {

    List<Film> selectListByActorId(@Param("actorIds") List<Integer> actorIds);

}