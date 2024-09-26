package com.birdsnail.nacos.config;

import com.purgeteam.dynamic.config.starter.util.PropertyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DynamicConfigApplicationAutoConfig {

    @Bean
    @ConditionalOnMissingBean(PropertiesUtil.class)
    public PropertyUtil propertyUtil() {
        return new PropertyUtil();
    }

}
