package com.birdsnail.env;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.reflections.scanners.Scanners.MethodsAnnotated;

/**
 * 更新@value的配置值
 */
public class UpdateValueEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static String key = "sso.login.exclusions";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        easyUpdate(environment, application);
    }

    /**
     * 简单的方式更新配置值
     */
    private void easyUpdate(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        PropertySource<?> targetSource = null;
        // 找到key所属的PropertySource
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource.containsProperty(key)) {
                targetSource = propertySource;
                break;
            }
        }
        if (targetSource == null) {
            return;
        }
        String oldValue = Objects.toString(targetSource.getProperty(key), null);
        if (targetSource instanceof MapPropertySource source) {
            source.getSource().put(key, getNewValue(oldValue));
        } else {
            Map<String, Object> map = Map.of(key, getNewValue(oldValue));
            MapPropertySource mapPropertySource = new MapPropertySource("更新后的PropertySource", map);
            // 将我们的自定义的
            propertySources.addBefore(targetSource.getName(), mapPropertySource);
        }
    }

    // 生成sso.login.exclusions的新值
    private String getNewValue(String oldValue) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().forPackage("com.birdsnail.controller").setScanners(MethodsAnnotated));
        // 获取有@NotLogin注解的方法
        Set<Method> resources =
                reflections.get(MethodsAnnotated.with(NotLogin.class).as(Method.class));
        StringBuilder sb = new StringBuilder(oldValue);
        for (Method method : resources) {
            RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
            if (requestMapping != null) { // 保证该方法是一个endPoint
                NotLogin notLogin = AnnotationUtils.findAnnotation(method, NotLogin.class);
                String temp = "";
                if (notLogin != null && notLogin.path() != null && !notLogin.path().equals("")) {
                    temp = notLogin.path();
                } else {
                    // 将注解中配置的白名单追加到原有的配置值后面
                    temp = StringUtils.join((requestMapping.path()), ",");
                }

                if (!oldValue.endsWith(",")) {
                    sb.append(",");
                }
                sb.append(temp);
            }
        }

        return sb.toString();
    }

}
