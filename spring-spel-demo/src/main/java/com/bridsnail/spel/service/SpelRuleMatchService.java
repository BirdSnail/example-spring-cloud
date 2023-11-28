package com.bridsnail.spel.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SpelRuleMatchService implements ApplicationContextAware {

    private ConfigurableBeanFactory beanFactory;
    private BeanExpressionResolver beanExpressionResolver;
    private BeanExpressionContext beanExpressionContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beanFactory = ((AnnotationConfigApplicationContext) applicationContext).getBeanFactory();
        this.beanExpressionResolver = beanFactory.getBeanExpressionResolver();
        this.beanExpressionContext = new BeanExpressionContext(beanFactory, null);
    }

    public void testSpelValue(String valueExpression) {
        System.out.println(beanFactory.resolveEmbeddedValue(valueExpression));
    }

    //  todo 没有成功
    public void testSpelBean(String beanExpression) {
        Object evaluate = beanExpressionResolver.evaluate("@SimpleService", beanExpressionContext);
        System.out.println(evaluate);
    }

}
