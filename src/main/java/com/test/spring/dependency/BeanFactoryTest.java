package com.test.spring.dependency;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class BeanFactoryTest {

    /**
     * spring bean 默认生成 bean
     * {@link org.springframework.beans.factory.support.DefaultBeanNameGenerator} xml
     * {@link org.springframework.context.annotation.AnnotationBeanNameGenerator} 注解的方式
     * 普通 bean 的方式 Introspector.decapitalize()
     */
    @Test
    public void test(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.refresh();
        ConfigurableListableBeanFactory beanFactory = configApplicationContext.getBeanFactory();
        System.out.println(beanFactory);
        BeanFactory bean = configApplicationContext.getBean(BeanFactory.class);
        System.out.println(bean);

    }
}
