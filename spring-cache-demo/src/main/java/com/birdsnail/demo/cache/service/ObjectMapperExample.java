package com.birdsnail.demo.cache.service;

import com.alibaba.fastjson2.JSON;
import com.birdsnail.demo.cache.pojo.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ObjectMapperExample {


    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setName("name");
//        System.out.println(testWriteObject(List.of(user)));
        testJacksonRedisSerializer(Collections.singletonList(user));
//        testJacksonRedisSerializer(List.of(user));
    }

    /**
     * spring boot cache保存缓存内容到redis使用的序列化器，用于将对象转换成json，会在json中添加类型提示。redis存储json
     *
     * @param user
     */
    public static void testJacksonRedisSerializer(List<User> userList) {
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
        String str = new String(Objects.requireNonNull(redisSerializer.serialize(userList)));
        System.out.println(str);
    }


    /**
     * json里面嵌入类型信息
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String testWriteObject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeResolverBuilder<?> typer = classPropertyTypeResolverBuilder(objectMapper);
        objectMapper.setDefaultTyping(typer);
        byte[] bytes = objectMapper.writeValueAsBytes(object);
        String s1 = new String(bytes);
        System.out.println("class类型元数据-property：" + s1);
        List<User> users = objectMapper.readValue(s1, new TypeReference<List<User>>() {
        });
        System.out.println("反序列化后：" + JSON.toJSONString(users));

        ObjectMapper objectMapper2 = new ObjectMapper();
        typer = namePropertyTypeResolverBuilder(objectMapper);
        objectMapper2.setDefaultTyping(typer);
        bytes = objectMapper2.writeValueAsBytes(object);
        String s2 = new String(bytes);
        System.out.println("name类型元数据-property：" + s2);

        ObjectMapper objectMapper3 = new ObjectMapper();
        typer = classObjectTypeResolverBuilder(objectMapper3);
        objectMapper3.setDefaultTyping(typer);
        bytes = objectMapper3.writeValueAsBytes(object);
        String s3 = new String(bytes);
        System.out.println("wrapperObject类型元数据-property：" + s3);
        users = objectMapper3.readValue(s3, new TypeReference<>() {
        });
        System.out.println("反序列化后：" + JSON.toJSONString(users));

        return "";
    }

    private static TypeResolverBuilder<?> namePropertyTypeResolverBuilder(ObjectMapper objectMapper) {
        TypeResolverBuilder<?> typer2 = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.EVERYTHING,
                objectMapper.getPolymorphicTypeValidator());
        typer2 = typer2.init(JsonTypeInfo.Id.NAME, null); // 指定类型元数据信息为简易类名
        typer2 = typer2.inclusion(JsonTypeInfo.As.PROPERTY); // 将类型信息作为json里面的一个属性
        return typer2;
    }

    public static TypeResolverBuilder<?> classPropertyTypeResolverBuilder(ObjectMapper objectMapper) {
        TypeResolverBuilder<?> typer = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.EVERYTHING,
                objectMapper.getPolymorphicTypeValidator());
        typer = typer.init(JsonTypeInfo.Id.CLASS, null); // 指定类型元数据信息为class的全限定类名
        typer = typer.inclusion(JsonTypeInfo.As.PROPERTY); // 将类型信息作为json里面的一个属性
        return typer;
    }

    private static TypeResolverBuilder<?> classObjectTypeResolverBuilder(ObjectMapper objectMapper) {
        TypeResolverBuilder<?> typer = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.EVERYTHING,
                objectMapper.getPolymorphicTypeValidator());
        typer = typer.init(JsonTypeInfo.Id.CLASS, null); // 指定类型元数据信息为class的全限定类名
        typer = typer.inclusion(JsonTypeInfo.As.WRAPPER_OBJECT); // 将类型信息作为json里面的一个嵌套对象
        return typer;
    }


}
