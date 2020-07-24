package com.test.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

/**
 * @author lixiaoyu
 * @since 2020/7/24
 */
public class MyDestructionAwareBeanPostProcessor implements DestructionAwareBeanPostProcessor {

    /**
     * 销毁前的回调方法
     *
     * {@link org.springframework.context.annotation.CommonAnnotationBeanPostProcessor} 就是实现了这个接口
     * 将回调方法包装成 LifecycleElement
     * @PreDestory DisposableBean destroy method 就是在这回调
     *
     * @param bean
     * @param beanName
     * @throws BeansException
     */
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        System.out.println("bean 销毁");
    }
}
