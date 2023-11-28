package com.bridsnail.spel;

import com.bridsnail.spel.service.SpelRuleMatchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 使用spel进行验证
 */
@SpringBootApplication
public class SpringBootStartApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringBootStartApp.class);
        SpelRuleMatchService matchService = ctx.getBean(SpelRuleMatchService.class);
        matchService.testSpelValue("${spring.application.name:123}");
        matchService.testSpelBean("fsdaf");
    }

}
