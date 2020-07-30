package com.test.spring.reource;

import com.google.common.collect.ImmutableMap;
import com.test.spring.bean.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.MapPropertySource;

/**
 * @author lixiaoyu
 * @since 2020/7/29
 */
@PropertySource(value = "META-INF/person.yml", factory = YamlPropertySourceFactory.class)
public class TestMain {

    @Bean
    public Person person(@Value("${user.id}") String userId, @Value("${user.age}") int age){
        Person person = new Person();
        person.setUsername(userId);
        person.setAge(age);
        return person;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().getPropertySources().addFirst(new MapPropertySource("extProperties", ImmutableMap.of("user.name", "lixiaoyu")));
        context.register(TestMain.class);
        context.refresh();

        //System.out.println(context.getBean(Person.class));
        System.out.println(context.getEnvironment().getPropertySources());
    }
}
