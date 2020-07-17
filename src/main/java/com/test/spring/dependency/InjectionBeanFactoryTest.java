package com.test.spring.dependency;

import com.test.spring.bean.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 自动注入会从内建 beanMap 中获取 bean 但是主动拉取不会
 * org.springframework.beans.factory.support.DefaultListableBeanFactory:1264
 *
 *
 * @author lixiaoyu
 * @since 2020/7/1
 */
public class InjectionBeanFactoryTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();

        configApplicationContext.register(Person.class);

        configApplicationContext.refresh();

        Person bean1 = configApplicationContext.getBean(Person.class);

        System.out.println(bean1.getBeanFactory());

        BeanFactory bean = configApplicationContext.getBean(BeanFactory.class);

        System.out.println(bean);
    }
}
