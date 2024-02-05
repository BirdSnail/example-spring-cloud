package com.birdsnail.login.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil implements ApplicationContextAware {

    @Autowired
    private static ObjectMapper objectMapper;

    public static String toJsonStr(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        objectMapper = applicationContext.getBean(ObjectMapper.class);
    }

}
