package com.bridsnail.demo.mybatisplus.vo;

import com.bridsnail.demo.mybatisplus.entity.Film;
import lombok.Data;

import java.util.List;

@Data
public class ActorVO {

    private String firstName;

    private String lastName;

    /**
     * 出演电影
     */
    private List<Film> files;

}
