package com.birdsnail.multiple.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 多数据源配置。比较原始的用法，建议直接使用第三方框架dynamic-datasource-spring-boot3-starter
 */
@Configuration
@Slf4j
@ConditionalOnProperty(value = {"spring.datasource.local", "spring.datasource.dev"})
public class MultipleDataSourceConfiguration {

    /**
     * 本地数据库配置
     */
    @Bean
    @ConfigurationProperties("spring.datasource.local")
    public DataSourceProperties localDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * dev环境数据库配置
     */
    @Bean
    @ConfigurationProperties("spring.datasource.dev")
    public DataSourceProperties devDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource localDataSource(@Qualifier("localDataSourceProperties") DataSourceProperties localDataSourceProperties) {
        return localDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public DataSource devDataSource(@Qualifier("devDataSourceProperties") DataSourceProperties devDataSourceProperties) {
        return devDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "localTransactionManager")
    public DataSourceTransactionManager localTransactionManager(@Qualifier("localDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "devTransactionManager")
    public DataSourceTransactionManager devTransactionManager(@Qualifier("devDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
