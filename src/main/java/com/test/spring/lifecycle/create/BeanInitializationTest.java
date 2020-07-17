package com.test.spring.lifecycle.create;

import com.test.spring.bean.LifecycleBean;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lixiaoyu
 * @since 2020/7/1
 */
public class BeanInitializationTest {

    @Test
    public void testOrder(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(LifecycleBean.class);
        beanDefinitionBuilder.setInitMethodName("initMethod");
        beanDefinitionBuilder.setDestroyMethodName("destroyMethod");
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), configApplicationContext);
        configApplicationContext.refresh();
        configApplicationContext.close();
    }
}
