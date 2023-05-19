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