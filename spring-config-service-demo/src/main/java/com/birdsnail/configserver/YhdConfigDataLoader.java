package com.birdsnail.configserver;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * 自动义配置加载，从classpath上加载yhd.yaml配置文件
 */
public class YhdConfigDataLoader implements ConfigDataLoader<YhdConfigDataResource> {

    @Override
    public boolean isLoadable(ConfigDataLoaderContext context, YhdConfigDataResource resource) {
        return resource.getConfigServerName().equals(YhdConfigDataLocationResolver.PREFIX);
    }

    @Override
    public ConfigData load(ConfigDataLoaderContext context, YhdConfigDataResource resource)
            throws IOException, ConfigDataResourceNotFoundException {
        YamlPropertySourceLoader yamlPropertySourceLoader = getBean(context, YamlPropertySourceLoader.class);
        if (yamlPropertySourceLoader == null) {
            yamlPropertySourceLoader = new YamlPropertySourceLoader();
        }
        ClassPathResource cpResource = new ClassPathResource("yhd.yaml");
        List<PropertySource<?>> propertySources = yamlPropertySourceLoader
                .load("自定义的配置yhd.yaml, location-->classpath:/yhd.yaml" , cpResource);
        if (propertySources == null || propertySources.isEmpty()) {
            return null;
        }
        return new ConfigData(propertySources, ConfigData.PropertySourceOptions.ALWAYS_NONE);
    }

    protected <T> T getBean(ConfigDataLoaderContext context, Class<T> type) {
        if (context.getBootstrapContext().isRegistered(type)) {
            return context.getBootstrapContext().get(type);
        }
        return null;
    }

}
