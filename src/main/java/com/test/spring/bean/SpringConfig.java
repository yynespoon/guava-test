package com.test.spring.bean;

import org.springframework.context.annotation.Bean;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class SpringConfig {

    @Bean
    public Person person5(){
        return new Person();
    }
}
