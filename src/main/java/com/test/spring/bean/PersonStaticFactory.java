package com.test.spring.bean;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class PersonStaticFactory {

    public Person getPerson(){
        return new Person();
    }
}
