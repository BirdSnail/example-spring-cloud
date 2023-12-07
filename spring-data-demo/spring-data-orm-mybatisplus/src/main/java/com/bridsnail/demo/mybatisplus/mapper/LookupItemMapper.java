package com.bridsnail.demo.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bridsnail.demo.mybatisplus.entity.LookupItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LookupItemMapper extends BaseMapper<LookupItem> {
}