package com.test.spring.dependency;

import com.test.spring.bean.Person;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ObjectFactoryCreatingFactoryBean 实现了 FactoryBean
 * 内部构造 {@link org.springframework.beans.factory.ObjectFactory}
 *
 * @author lixiaoyu
 * @since 2020/7/2
 */
public class ObjectFactoryCreatingFactoryBeanTest {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext
                context = new AnnotationConfigApplicationContext();
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(ObjectFactoryCreatingFactoryBean.class).getBeanDefinition();
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.addPropertyValue("targetBeanName", "person");
        beanDefinition.setPropertyValues(mutablePropertyValues);
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, context);
        context.register(Person.class);
        context.refresh();

        ObjectFactoryCreatingFactoryBean bean = context.getBean(ObjectFactoryCreatingFactoryBean.class);
        System.out.println(bean.getObject().getObject());
    }
}
