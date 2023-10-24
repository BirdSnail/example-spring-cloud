package com.birdsnail.springbootdemo.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * spring关于注解的处理demo。查找所有被{@link YhdJob}注解的方法。
 */
@Component
@Slf4j
public class SpringAnnotationDemo implements SmartInitializingSingleton, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private List<Method> yhdjobMethods = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 在所有的单例bean初始化后执行
     */
    @Override
    public void afterSingletonsInstantiated() {
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            try {
                Map<Method, YhdJob> annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                        // 判断方法是否有YhdJob注解
                        (MethodIntrospector.MetadataLookup<YhdJob>) method -> AnnotatedElementUtils.findMergedAnnotation(method, YhdJob.class));
                yhdjobMethods.addAll(annotatedMethods.keySet());
            } catch (Throwable ex) {
                log.error("xxl-job method-jobhandler resolve error for bean[" + beanDefinitionName + "].", ex);
            }
        }
    }

    public void printYhdJobMethod() {
        for (Method yhdjobMethod : yhdjobMethods) {
            log.info(yhdjobMethod.getName());
        }
    }

}
