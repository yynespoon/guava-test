package com.test.spring;

import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class BuildBeanDefinitionTest {

    @Test
    public void testWithoutName(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();

        BeanDefinitionReaderUtils.registerWithGeneratedName(
                BeanDefinitionBuilder.genericBeanDefinition(BeanFactoryTest.class).getBeanDefinition(),
                configApplicationContext
        );

        configApplicationContext.refresh();
        System.out.println(configApplicationContext.getBeansOfType(BeanFactoryTest.class));
    }
}
