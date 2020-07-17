package com.test.spring.dependency;

import com.test.spring.bean.Person;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;

import java.util.Collection;

/**
 * @author lixiaoyu
 * @since 2020/7/2
 */
public class GroupByQualifierTest {

    @Autowired
    private Collection<Person> collection2;

    @Autowired
    @Qualifier
    private Collection<Person> collection;

    @Autowired
    private ObjectProvider<Person> objectProvider;

    @Bean
    @Qualifier
    public Person person1(){
        return new Person("1");
    }

    @Bean
    public static Person person2(){
        return Person.getPerson("2");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext
                context = new AnnotationConfigApplicationContext();

        context.register(GroupByQualifierTest.class);
        context.refresh();
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        System.out.println(context.getBean(GroupByQualifierTest.class).collection);
        System.out.println(context.getBean(GroupByQualifierTest.class).collection2);
        System.out.println(context.getBean(GroupByQualifierTest.class).objectProvider.getIfUnique());

        ResolvableType resolvableType = ResolvableType.forRawClass(GroupByQualifierTest.class).asMap();

    }

}
