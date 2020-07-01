package com.test.spring.create;

import org.springframework.stereotype.Component;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
@Component
public class Person {

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

    public static Person getPerson(){
        return new Person();
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
