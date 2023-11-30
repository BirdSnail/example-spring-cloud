# SpringBoot多数据源配置
使用MybatisPlus框架提供的动态数据源.
```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot3-starter</artifactId>
    <version>4.1.3</version>
</dependency>
```
注意版本的对应，`spring-boot3.x`版本要使用dynamic-datasource-spring-boot3-starter。`spring-boot2.x`版本使用dynamic-datasource-spring-boot-starter

## 动态数据源原理
### DynamicRoutingDataSource
该类将spring容器中的所有`DataSource`保存到一个map中，map的key是配置文件中的数据库实例名称。它本身实现了`DataSource`接口，可以看作是一个代理类，实际获取DataSource的过程会调用`determineDataSource`方法。
```java
public DataSource determineDataSource() {
    String dsKey = DynamicDataSourceContextHolder.peek();
    return getDataSource(dsKey);
}
```
DynamicDataSourceContextHolder是一个ThreadLocal封装类，根据当前线程保存的key获取关联的DataSource

### DS，DynamicDataSourceAnnotationInterceptor
使用了AOP功能，被`DS`注解的方法和类会被拦截, 会根据DS的value解析出DataSource对应的key，并保存到`DynamicDataSourceContextHolder`中，后续就可以从中get了。
```java
   public Object invoke(MethodInvocation invocation) throws Throwable {
        String dsKey = determineDatasourceKey(invocation);
        DynamicDataSourceContextHolder.push(dsKey);
        try {
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }
```


# 多数据源下的事务问题
### 问题1：多数据源失效
最外层开启普通的事务注解`@Transactional`会导致多数据源切换失效，必须借助MybatisPlus的事务注解`@DSTransactional`.
```java
@DSTransactional
public void testDsTransaction() {
    dbService.updateLocalData(false, 2);
    dbService.updateDevData(true, 112L);
}
```
### 问题2：整体无法保证是一个事务
在上述代码中即使`updateDevData`执行出错，也只能保证`updateDevData`方法里的事务可以回滚，而对于`updateLocalData`事务已经提交，即使后续出错也不会进行回滚操作，造成脏数据。

对于这中跨数据库实例的事务，要解决此问题必须借助分布式事务框架。

# Seata分布式事务框架
## 1. 启动server端
server端的部署有多种模式，具体看[官方文档](https://seata.io/zh-cn/docs/ops/deploy-guide-beginner)
### 方式1：直接部署
没什么好说的，直接启动即可

### 方式2：Nacos作为注册中心和配置中心进行部署
#### 踩坑
- nacos的namespace配置要填id，不能是名称
## 2. client端接入