package com.test.spring.dependency;

import com.test.spring.bean.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 层次性的 bean 查找
 *
 * @author lixiaoyu
 * @since 2020/7/1
 */
public class HierarchicalBeanFactoryTest {

    private AnnotationConfigApplicationContext context;

    @Before
    public void before(){

        AnnotationConfigApplicationContext
                parent = new AnnotationConfigApplicationContext();
        parent.register(Person.class);
        parent.refresh();

        AnnotationConfigApplicationContext
                subContainer = new AnnotationConfigApplicationContext();
        subContainer.setParent(parent);
        subContainer.refresh();
        context = subContainer;
    }

    /**
     * ListableBeanFactory 中的方法不会考虑父容器中的 bean
     * 如果要以集合的方式查找 bean 要使用 {@link BeanFactoryUtils}
     */
    @Test
    public void test(){
        if (context.containsLocalBean("person")) {
            System.out.println(BeanFactoryUtils.beanOfType(context, Person.class));
        }
        System.out.println(context.getBeansOfType(Person.class));
        System.out.println(BeanFactoryUtils.beanOfTypeIncludingAncestors(context, Person.class));
        System.out.println(context.getBeanProvider(Person.class).getIfAvailable());
        System.out.println(context.getBean(Person.class));
    }
}
