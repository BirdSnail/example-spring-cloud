package com.birdsnail;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Field;

/**
 * @author hfx
 * @version 1.0.0
 * @Description:
 * @Date: 2023/04/07 15:42
 */
public class ConfigRefreshDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException {
        String configName = "test";
        String configValue = "vvvv";

        // 注册一个bean a
        BeanDefinition bdf = BeanDefinitionBuilder.genericBeanDefinition(A.class).getBeanDefinition();
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("a", bdf);

        // 遍历容器中所有的bean
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String beanClassName = beanDefinition.getBeanClassName();
            Class clazz = Class.forName(beanClassName);
            Object beanInstance = beanFactory.getBean(beanName);
            if (beanInstance == null) {
                continue;
            }
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Value.class)) {
                    Value value = field.getAnnotation(Value.class);
                    if (configName.equals(value.value())) {
                        printDogName(beanInstance);
                        System.out.println("before refresh:" + beanInstance);
                        // 这个属性值要更新
                        field.set(beanInstance, configValue);
                        // 要通过set方法更新值 先搞成public属性 试试
                        System.out.println(" after refresh:" + beanInstance);
                        printDogName(beanInstance);
                    }
                }
            }
        }
    }

    private static void printDogName(Object beanInstance) {
        if (beanInstance instanceof A aObj) {
            System.out.println("修狗的名字：" +  aObj.getDog().getName());
        }
    }

    @Data
    public static class A {

        @Value("test")
        public String i = "test init";

        private Dog dog;

        public A() {
            dog = new Dog(i);
        }

    }

    public static class Dog {

        private String name;

        public Dog(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
