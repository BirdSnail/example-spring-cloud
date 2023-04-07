# 使用spring.config.import导入自定义的配置项
## Importing扩展配置
spring boot在2.4版本提供了新的加载配置项的方式，可以直接在 application.properties 或 application.yml 文件中使用新的`spring.config.import`属性。例如希望导入一个 "忽略的 git" 的 developer.properties 文件，以便团队中的任何开发人员都可以快速更改属性：
```properties
application.name=myapp
spring.config.import=developer.properties
```
spring.config.import的值与url语法一样，如果你没有指定前缀，则它被视为导入一个文件或者文件夹。但是如果你使用了`nacso:`前缀，则告诉Spring Boot你期望在此位置使用nacos的配置服务：
```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: localhost:8848
        file-extension: yaml
        group: dev
        username: nacos
        password: nacos
  config:
    import:
#       拉取nacos配置：dataId=person, group=dev
      - nacos:person?group=dev
```
## 实现原理
以加载developer.properties为例：
```properties
spring.config.import=developer.properties
```
主要分为两步
1. `ConfigDataLocationResolver`接口用于资源定位解析，获取`ConfigDataResource`
2. `ConfigDataLoader`接口实际加载配置项到容器中


### ConfigDataLocationResolver
import的每一个配置都被会被封装成`ConfigDataLocation`,该接口负责解析资源定位，获取对应的`ConfigDataResource`
```java
public interface ConfigDataLocationResolver<R extends ConfigDataResource> {
    
	boolean isResolvable(ConfigDataLocationResolverContext context, ConfigDataLocation location);

    /**
     * 解析location
     */
	List<R> resolve(ConfigDataLocationResolverContext context, ConfigDataLocation location)
			throws ConfigDataLocationNotFoundException, ConfigDataResourceNotFoundException;

    /**
     * 同样是解析location，但是可以访问spring.profiles的配置项
     */
	default List<R> resolveProfileSpecific(ConfigDataLocationResolverContext context, ConfigDataLocation location,
			Profiles profiles) throws ConfigDataLocationNotFoundException {
		return Collections.emptyList();
	}
}
```
`isResolvable`方法判断这个location是否可以解析。`ConfigDataLocationResolver`有一个标准的实现类`StandardConfigDataLocationResolver`,该方法固定返回true，因此可以解析所有的location。

### ConfigDataResource
代表一个配置项资源，继承该类必须实现有效的 equals和 hashCode toString 方法。主要是为了标识资源的唯一性，防止资源被重复加载。

```java
public abstract class ConfigDataResource {

	private final boolean optional;

	/**
	 * Create a new non-optional {@link ConfigDataResource} instance.
	 */
	public ConfigDataResource() {
		this(false);
	}

	/**
	 * Create a new {@link ConfigDataResource} instance.
	 * @param optional if the resource is optional
	 * @since 2.4.6
	 */
	protected ConfigDataResource(boolean optional) {
		this.optional = optional;
	}

	boolean isOptional() {
		return this.optional;
	}

}
```
这个类只有一个`optional`字段，用于标记该资源是否是可选的，可通过import时设置：
```properties
spring.config.import=optional:developer.properties
```

### ConfigDataLoader
有了资源以后就可以进行加载了，因为spring中所有的配置项都被封装成了PropertySource，因此这个类也不例外的要将`ConfigDataResource`转化为`PropertySource`.
```java
public interface ConfigDataLoader<R extends ConfigDataResource> {

	/**
	 * Returns if the specified resource can be loaded by this instance.
	 * @param context the loader context
	 * @param resource the resource to check.
	 * @return if the resource is supported by this loader
	 */
	default boolean isLoadable(ConfigDataLoaderContext context, R resource) {
		return true;
	}

	/**
	 * Load {@link ConfigData} for the given resource.
	 * @param context the loader context
	 * @param resource the resource to load
	 * @return the loaded config data or {@code null} if the location should be skipped
	 * @throws IOException on IO error
	 * @throws ConfigDataResourceNotFoundException if the resource cannot be found
	 */
	ConfigData load(ConfigDataLoaderContext context, R resource)
			throws IOException, ConfigDataResourceNotFoundException;

}
```
ConfigData包装了PropertySource。

在确定一个ConfigDataResource被哪一个ConfigDataLoader加载时，spring用了一点小技巧。ConfigDataLoader会接受一个泛型，这个类型代表着某个具体的资源类型，因此一个`ConfigDataLoader`实现类会绑定一个`ConfigDataResource`实现类。Spring会通过自己的SPI机制获取到所有`ConfigDataLoader`实现类，要决定一个Resource使用哪个Loader进行加载时只需要遍历所有的`ConfigDataLoader`,判断该Loader的泛型是不是跟需要解析的`ConfigDataResource`是同一个类型即可。具体代码在`ConfigDataLoaders`：
```java
private <R extends ConfigDataResource> ConfigDataLoader<R> getLoader(ConfigDataLoaderContext context, R resource) {
    ConfigDataLoader<R> result = null;
    for (int i = 0; i < this.loaders.size(); i++) {
        ConfigDataLoader<?> candidate = this.loaders.get(i);
        // 判断传入进来的resource是否为loader绑定的resource的实例
        if (this.resourceTypes.get(i).isInstance(resource)) {
            ConfigDataLoader<R> loader = (ConfigDataLoader<R>) candidate;
            if (loader.isLoadable(context, resource)) {
                if (result != null) {
                    throw new IllegalStateException("Multiple loaders found for resource '" + resource + "' ["
                            + candidate.getClass().getName() + "," + result.getClass().getName() + "]");
                }
                result = loader;
            }
        }
    }
    Assert.state(result != null, () -> "No loader found for resource '" + resource + "'");
    return result;
}
```

找到合适`ConfigDataLoader`后就调用`load()`执行实际的加载.这里面又会进一步委托给各种`ProperSourceLoader`来加载配置文件。
