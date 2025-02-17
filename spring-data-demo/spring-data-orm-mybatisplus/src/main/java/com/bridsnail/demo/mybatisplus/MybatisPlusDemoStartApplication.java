package com.bridsnail.demo.mybatisplus;

import com.bridsnail.demo.mybatisplus.service.ActorFilmService;
import com.bridsnail.demo.mybatisplus.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class MybatisPlusDemoStartApplication implements CommandLineRunner {

    @Autowired
    private ActorFilmService actorFilmService;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MybatisPlusDemoStartApplication.class, args);
        DictionaryService dictionaryService = applicationContext.getBean(DictionaryService.class);
        dictionaryService.updateEconKind();
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
