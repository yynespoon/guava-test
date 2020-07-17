package com.test.spring.lifecycle.create;

import com.test.spring.bean.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lixiaoyu
 * @since 2020/7/1
 */
public class GetBeanFromBeanFactoryTest {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @Before
    public void before(){
        context.register(Person.class);
        context.refresh();
    }

    /**
     * 通常获取 bean 的方法
     */
    @Test
    public void testGetBeanByBeanName(){
        System.out.println(context.getBean(Person.class));
        System.out.println(context.getBean("person"));
    }

    /**
     * 延迟获取不会抛异常
     */
    @Test
    public void testGetBeanByObjectFactory(){
        System.out.println(context.getBeanProvider(BeanFactory.class).getIfAvailable());
        // 如果获取不到调用 supplier
        System.out.println(context.getBeanProvider(Person.class).getIfAvailable(Person::new));
        // 如果不是唯一调用 supplier
        System.out.println(context.getBeanProvider(Person.class).getIfUnique(Person::new));

    }

    /**
     * 这种方式获取 bean 会触发 bean 的初始化
     */
    @Test
    public void testGetBeansOfType(){
        Map<String, Person> beansOfType = context.getBeansOfType(Person.class);
        System.out.println(beansOfType);
    }

    /**
     * 获取 beanName 不会触发 bean 的初始化
     */
    @Test
    public void testGetBeanNameOfType(){
        System.out.println(context.getBeanNamesForType(Person.class));
    }

    /**
     * 通过注解获取 bean
     */
    @Test
    public void testGetBeanByAnnotation(){
        System.out.println(context.getBeansWithAnnotation(Component.class));
    }
}
