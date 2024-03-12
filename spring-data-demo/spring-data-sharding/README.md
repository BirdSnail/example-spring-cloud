# Sharding-JDBC分库分表实践

### Maven依赖
spring-boot3.x版本使用如下的版本依赖。
```xml
<!-- https://mvnrepository.com/artifact/org.apache.shardingsphere/shardingsphere-jdbc-core-spring-boot-starter -->
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
    <version>5.2.1</version>
</dependency>

```

### 分库分表方式
#### 1. 手动分库分表
要对数据表的分布情况了然于心，才能写出正确的 actualDataNodes 规则。
#### 2. 自动分库分表
交由 ShardingSphere 自动管理分片，用户只需要指定分片数量和使用的数据源，无需再关心表的具体分布