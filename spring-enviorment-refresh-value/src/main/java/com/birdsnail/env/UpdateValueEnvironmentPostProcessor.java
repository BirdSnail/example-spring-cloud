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
import org.springframework.web.bind.annotation.GetMapping;
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

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        easyUpdate(environment, application);
    }

    /**
     * 简单的方式更新配置值
     */
    private void easyUpdate(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        String k = "yhd.name";
        PropertySource<?> targetSource = null;
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource.containsProperty(k)) {
                targetSource = propertySource;
                break;
            }
        }
        if (targetSource == null) {
            return;
        }
        String oldValue = Objects.toString(targetSource.getProperty(k), null);
        if (targetSource instanceof MapPropertySource source) {
            source.getSource().put(k, getNewValue(oldValue));
        } else {
            Map<String, Object> map = Map.of(k, getNewValue(oldValue));
            MapPropertySource mapPropertySource = new MapPropertySource("k_source_yhd", map);
            propertySources.addBefore(targetSource.getName(), mapPropertySource);
        }
    }

    private String getNewValue(String oldValue) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().forPackage("com.birdsnail.controller").setScanners(MethodsAnnotated));
        Set<Method> resources =
                reflections.get(MethodsAnnotated.with(GetMapping.class).as(Method.class));
        StringBuilder sb = new StringBuilder(oldValue);
        for (Method method : resources) {
            RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
            if (requestMapping != null) {
                NotLogin notLogin = AnnotationUtils.findAnnotation(method, NotLogin.class);
                String temp = "";
                if (notLogin != null && notLogin.path() != null && !notLogin.path().equals("")) {
                    temp = notLogin.path();
                } else {
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
