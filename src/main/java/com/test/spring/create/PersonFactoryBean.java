package com.test.spring.create;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class PersonFactoryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
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
