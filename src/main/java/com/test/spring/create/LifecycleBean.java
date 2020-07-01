package com.test.spring.create;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author lixiaoyu
 * @since 2020/7/1
 */
public class LifecycleBean implements InitializingBean, DisposableBean {

    /**
     * init first
     * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory:1622
     */
    @PostConstruct
    public void postConstructInit(){
        System.out.println("init first");
    }

    /**
     * init second
     * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory:1626
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("init second");
    }

    /**
     * init third
     * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory:1626
     */
    public void initMethod(){
        System.out.println("init third");
    }

    /**
     * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory:597
     * 将包含该方法的 拦截器 {@link org.springframework.context.annotation.CommonAnnotationBeanPostProcessor}包装成 DisposableBeanAdapter
     * 销毁时调用该拦截器
     */
    @PreDestroy
    public void preDestroy(){
        System.out.println("destroy first");
    }

    /**
     * destroy second
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy second");
    }

    /**
     * destroy third
     */
    public void destroyMethod(){
        System.out.println("destroy third");
    }
}
