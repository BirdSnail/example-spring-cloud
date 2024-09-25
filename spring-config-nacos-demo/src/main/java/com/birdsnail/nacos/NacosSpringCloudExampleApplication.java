package com.birdsnail.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class NacosSpringCloudExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(NacosSpringCloudExampleApplication.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        String userName = environment.getProperty("person.name");
//        String userAge = environment.getProperty("person.age");
//        String dog = environment.getProperty("dog.name");
//        System.out.println("dog:" + dog);
//        String cat = environment.getProperty("cat.name");
//        System.out.println("cat:" + cat);
//        System.err.println("user name :" + userName + "; age: " + userAge);
//
//        System.out.println("=========spring config group分组测试=============");
//        System.out.printf("group1-->{name:%s, desc:%s}%n", environment.getProperty("test.group1.name"), environment.getProperty("test.group1.desc"));
//        System.out.printf("group2-->{name:%s, desc:%s}%n", environment.getProperty("test.group2.name"), environment.getProperty("test.group2.desc"));

        System.out.println("=========spring config nacos配置=============");
        System.out.println(environment.getProperty("cat.name"));
        System.out.println(environment.getProperty("demo1.name"));

    }


}
