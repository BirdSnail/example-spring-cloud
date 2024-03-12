package com.birdsnail.demo.sharding;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@Slf4j
@MapperScan(basePackages = {"com.birdsnail.demo.sharding.dao"})
public class ShardingJdbcDemoStartApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDemoStartApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
//        ActorQueryParam param = new ActorQueryParam();
//        param.setPageNum(1);
//        param.setFirstName("WHOOPI");
//        param.setPageSize(10);
//        List<ActorVO> actorVOS = actorFilmService.queryActor(param);
//        log.info("查询演员response:\n{}", JSON.toJSONString(actorVOS, JSONWriter.Feature.PrettyFormat));
//
//        Page<Actor> page = actorFilmService.queryActorPage(param);
//        log.info("分页查询演员response:\n{}", JSON.toJSONString(page, JSONWriter.Feature.PrettyFormat));
    }
}
