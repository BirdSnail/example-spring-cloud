package com.bridsnail.demo.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bridsnail.demo.mybatisplus.entity.Actor;
import com.bridsnail.demo.mybatisplus.entity.Film;
import com.bridsnail.demo.mybatisplus.entity.FilmActor;
import com.bridsnail.demo.mybatisplus.mapper.ActorMapper;
import com.bridsnail.demo.mybatisplus.mapper.FilmActorMapper;
import com.bridsnail.demo.mybatisplus.mapper.FilmMapper;
import com.bridsnail.demo.mybatisplus.vo.ActorQueryParam;
import com.bridsnail.demo.mybatisplus.vo.ActorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorFilmService {

    private ActorMapper actorMapper;

    private FilmMapper filmMapper;

    private FilmActorMapper filmActorMapper;

    @Autowired
    public void setActorMapper(ActorMapper actorMapper) {
        this.actorMapper = actorMapper;
    }

    @Autowired
    public void setFilmMapper(FilmMapper filmMapper) {
        this.filmMapper = filmMapper;
    }

    @Autowired
    public void setFilmActorMapper(FilmActorMapper filmActorMapper) {
        this.filmActorMapper = filmActorMapper;
    }

    public List<ActorVO> queryActor(ActorQueryParam actorQueryParam) {
        LambdaQueryWrapper<Actor> lambda = Wrappers.lambdaQuery();
        lambda.select(Actor::getFirstName, Actor::getLastName, Actor::getActorId)
                .last("limit " + actorQueryParam.getPageSize());
        if (actorQueryParam.getFirstName() != null) {
            lambda.eq(Actor::getFirstName, actorQueryParam.getFirstName());
        }
        if (actorQueryParam.getActorId() != null) {
            lambda.eq(Actor::getActorId, actorQueryParam.getActorId());
        }

        List<ActorVO> res = new ArrayList<>();

        List<Actor> actorList = actorMapper.selectList(lambda);
        for (Actor actor : actorList) {
            ActorVO actorVO = new ActorVO();
            actorVO.setFirstName(actor.getFirstName());
            actorVO.setLastName(actor.getLastName());

            LambdaQueryWrapper<FilmActor> filmActorWrapper = Wrappers.lambdaQuery();
            List<FilmActor> filmActors = filmActorMapper.selectList(filmActorWrapper);

            LambdaQueryWrapper<Film> filmWrapper = Wrappers.lambdaQuery();
            List<Integer> filmIds = filmActors.stream().map(FilmActor::getFilmId).toList();
            filmWrapper.in(Film::getFilmId, filmIds);
            List<Film> films = filmMapper.selectList(filmWrapper);
            actorVO.setFiles(films);

            res.add(actorVO);
        }

        return res;
    }

    public Page<Actor> queryActorPage(ActorQueryParam actorQueryParam) {
        Page<Actor> page = Page.of(actorQueryParam.getPageNum(), actorQueryParam.getPageSize());
        LambdaQueryWrapper<Actor> wrapper = Wrappers.lambdaQuery();
        if (actorQueryParam.getFirstName() != null) {
            wrapper.eq(Actor::getFirstName, actorQueryParam.getFirstName());
        }
        return actorMapper.selectPage(page, wrapper);
    }

}
