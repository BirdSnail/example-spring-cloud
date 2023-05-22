package com.birdsnail.demo.cache.config;

import com.birdsnail.demo.cache.pojo.User;
import com.birdsnail.demo.cache.service.ObjectMapperExample;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Objects;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer() {
        RedisSerializationContext.SerializationPair<Object> jsonSerializer =
//                RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json());
                RedisSerializationContext.SerializationPair.fromSerializer(json());
        return (builder) -> builder
                .withCacheConfiguration("redis-cache", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(60))
                        .serializeValuesWith(jsonSerializer))
                .withCacheConfiguration("redis-cache2", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(1))
                        .serializeValuesWith(jsonSerializer));
    }

    public static RedisSerializer<Object> json() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
        TypeResolverBuilder<?> typeResolverBuilder = ObjectMapperExample.classPropertyTypeResolverBuilder(objectMapper);
        objectMapper.setDefaultTyping(typeResolverBuilder);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("name");
        RedisSerializer<Object> jsonSerializer = json();
        String s = new String(Objects.requireNonNull(jsonSerializer.serialize(user)));
        System.out.println(s);
    }
}
