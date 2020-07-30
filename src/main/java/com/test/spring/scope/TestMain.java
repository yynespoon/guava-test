package com.test.spring.scope;

import com.test.spring.bean.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @author lixiaoyu
 * @since 2020/7/23
 */
public class TestMain {

    @Bean
    @Scope("threadLocalScope")
    public Person person() {
        return new Person();
    }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext
                context = new AnnotationConfigApplicationContext();
        context.getBeanFactory().registerScope("threadLocalScope", new ThreadLocalScope());
        context.register(TestMain.class);
        context.refresh();

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(() -> {
                System.out.println(System.identityHashCode(context.getBean("person")));
            });
            thread.start();
            thread.join();
        }

        context.close();
    }


}
