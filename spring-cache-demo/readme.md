# SpringBoot缓存
## 使用缓存
在方法上添加`@Cacheable`注解

两个接口：  
`org.springframework.cache.Cache`和`org.springframework.cache.CacheManager`。缓存的实现取决与这个两个接口的具体实现，实现这两个接口可以自定义缓存控制。

## spring支持的缓存
当我们没有指定具体的`CacheManager`时，spring boot会尝试检测下面的提供程序
1. Generic
2. JCache (JSR-107) (EhCache 3, Hazelcast, Infinispan, and others)
3. EhCache 2.x
4. Hazelcast
5. Infinispan
6. Couchbase
7. Redis
8. Caffeine
9. Simple

## 使用redis作为缓存存储
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
当我们的项目redis的配置可用时，RedisCacheManager缓存管理器会被自动配置。我们可以注册一个bean来完全控制redis缓存的行为。
```java
@Configuration(proxyBeanMethods = false)
public class MyRedisCacheManagerConfiguration {

    @Bean
    public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("cache1", RedisCacheConfiguration
                        .defaultCacheConfig().entryTtl(Duration.ofSeconds(10)))
                .withCacheConfiguration("cache2", RedisCacheConfiguration
                        .defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));

    }

}
```
### 方法返回类型是List引起的报错
```java
@Cacheable(value = "redis-cache2", key = "#root.methodName + '-' + #name")
public List<User> findAllUserByName(String name) {
    log.info("获取user by Name, param:{}", name);
    User user = new User();
    user.setId(1000);
    user.setName(name);
    user.setAge(28);
    return List.of(user);
}
```
以上代码会报错:`com.fasterxml.jackson.databind.exc.InvalidTypeIdException: Could not resolve subtype of [simple type, class java.lang.Object]: missing type id property '@class'`.
List.of()是Java17提供的新的api，返回的List是一个final修饰的List，这回导致没有类型元数据信息嵌入到json中。
```java
[
  {
    "@class":"com.birdsnail.demo.cache.pojo.User",
    "id":1000,
    "name":"yhd",
    "phone":null,
    "age":28
  }
]
```
如果返回ArrayList
```java
public List<User> findAllUserByName(String name) {
    log.info("获取user by Name, param:{}", name);
    User user = new User();
    user.setId(1000);
    user.setName(name);
    user.setAge(28);
    List<User> res = new ArrayList<>();
    res.add(user);
    return res;
}
```
保存到redis时的内容：
```java
[
  "java.util.ArrayList",
  [
    {
      "@class":"com.birdsnail.demo.cache.pojo.User",
      "id":1000,
      "name":"yhd",
      "phone":null,
      "age":28
    }
  ]
]
```

