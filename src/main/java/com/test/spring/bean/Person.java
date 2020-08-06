package com.test.spring.bean;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
@Component
public class Person {

    private BeanFactory beanFactory;

    private String username;

    private int age;

    private Properties propertiesContext;

    private Map<String, String> mapContext;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String username) {
        this.username = username;
    }

    public Person() {
    }

    public static Person getPerson(){
        return new Person();
    }

    public static Person getPerson(String username){
        return new Person(username);
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public Properties getPropertiesContext() {
        return propertiesContext;
    }

    public void setPropertiesContext(Properties propertiesContext) {
        this.propertiesContext = propertiesContext;
    }

    public Map<String, String> getMapContext() {
        return mapContext;
    }

    public void setMapContext(Map<String, String> mapContext) {
        this.mapContext = mapContext;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("beanFactory=").append(beanFactory);
        sb.append(", username='").append(username).append('\'');
        sb.append(", age=").append(age);
        sb.append(", propertiesContext=").append(propertiesContext);
        sb.append(", mapContext=").append(mapContext);
        sb.append('}');
        return sb.toString();
    }
}
