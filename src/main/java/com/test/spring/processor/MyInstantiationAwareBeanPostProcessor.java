package com.test.spring.processor;

import com.test.spring.bean.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * @author lixiaoyu
 * @since 2020/7/24
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    /**
     * 主要在 bean 的实例化前 如果返回的 Object 不为空则不进行 bean 的创建
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(ObjectUtils.nullSafeEquals(beanName, "person")){
            return new Person("张三");
        }
        return null;
    }

    /**
     * 属性注入前做一些事情 如果返回 false 则不再进行属性注入
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }

    /**
     * 生成属性注入 PropertyValues 后到未注入前对注入属性的操作
     * {@link org.springframework.beans.factory.annotation.Autowired} 与 {@link javax.annotation.Resource}
     * 就是基于这个拦截器实现的注入属性的生成
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return null;
    }

    /**
     * bean 初始化前的操作 {@link javax.annotation.PostConstruct} 和 ApplicationContext 中 {@link org.springframework.beans.factory.Aware}
     * 接口的回调是在这里完成的
     *
     * BeanFactory 中 Aware 回调与 ApplicationContext 的回调接口不相同
     * BeanFactory 中 回调只包含
     * {@link org.springframework.beans.factory.BeanNameAware}
     * {@link org.springframework.beans.factory.BeanClassLoaderAware}
     * {@link org.springframework.beans.factory.BeanFactoryAware}
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before bean init");
        return null;
    }

    /**
     * 初始化之后调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after bean init");
        return null;
    }
}
