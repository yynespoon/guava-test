package com.test.spring.bean;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class PersonFactoryBean implements FactoryBean<Person> {

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public Person getObject() throws Exception {
        System.out.println(beanFactory);
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
