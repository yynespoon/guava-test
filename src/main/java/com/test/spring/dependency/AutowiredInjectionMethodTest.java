package com.test.spring.dependency;

import com.test.spring.bean.Person;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lixiaoyu
 * @since 2020/7/3
 */
public class AutowiredInjectionMethodTest {


    //@Autowired
    public void setPer(Person person){
        System.out.println(person);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext
                context = new AnnotationConfigApplicationContext();

        //context.register(Person.class);
        context.register(AutowiredInjectionMethodTest.class);

        //context.getBeanFactory().registerSingleton("abc", new Person());

        //context.getBeanFactory().registerResolvableDependency(Person.class, new Person());

        context.refresh();
        context.registerBeanDefinition("aaa", BeanDefinitionBuilder.genericBeanDefinition(Person.class).getBeanDefinition());

        System.out.println(context.getBean("aaa"));

        //System.out.println(context.getBean(Person.class));

        System.out.println(context.getBean(AutowiredInjectionMethodTest.class));
    }
}
