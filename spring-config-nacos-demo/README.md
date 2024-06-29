## Nacos配置中心用法

### 1. 共享通用配置
项目通用配置可以放在配置中心，作为一个单独的通用配置文件，各个项目引入这个通用配置
```yaml
spring:
  config:
    import:
      # 拉取nacos配置：传入dataId
      - nacos:commmon.yaml?group=common_config
      - classpath:dog.yaml
```
`commmon.yaml?group=common_config`中的group为可选项，不写的话默认为`DEFAULT_GROUP`

### 2. 动态刷新配置
Nacos后台更新了配置，应用服务可以感知到变化，实时从Nacos拉取最新配置值。 

#### 2.1 在配置类上使用`@RefreshScope`注解
```java
@Component
@ConfigurationProperties(prefix = "xxx")
@RefreshScope // 支持配置值动态刷新
public class TestProperties {
    private String name;
}
```

#### 2.2 `@Value`注解动态属性
在使用`@Value`注解的字段所在的类上面使用`@RefreshScope`注解

```java

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class TestService {

    @Value("#{test.name}")
    private string name;

    public String getName() {
        return name;
    }
}

```

### 3. Nacos配置中心和本地项目application.yml有相同的属性配置时，谁会生效？
Nacos中的配置生效