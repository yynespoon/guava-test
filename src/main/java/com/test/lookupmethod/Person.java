package com.test.lookupmethod;

import java.lang.invoke.MethodHandles;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class Person {

    private String username;

    private int age;

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    public static MethodHandles.Lookup lookup(){
        return MethodHandles.lookup();
    }
}
