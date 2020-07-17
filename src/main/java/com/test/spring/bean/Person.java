package com.test.spring.bean;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
@Component
public class Person {

    private BeanFactory beanFactory;

    private String username;

    private int age;

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

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
