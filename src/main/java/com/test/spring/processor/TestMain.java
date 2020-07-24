package com.test.spring.processor;

import com.test.spring.bean.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lixiaoyu
 * @since 2020/7/24
 */
public class TestMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext();
        context.getBeanFactory().addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
        context.register(Person.class);
        context.refresh();

        System.out.println(context.getBean(Person.class));
    }
}
