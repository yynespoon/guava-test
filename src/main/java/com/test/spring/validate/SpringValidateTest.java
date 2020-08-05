package com.test.spring.validate;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Locale;

/**
 * @author lixiaoyu
 * @since 2020-08-05 16:43
 */
@Validated
public class SpringValidateTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext
                context = new AnnotationConfigApplicationContext();
        //国际化内容
        StaticMessageSource staticMessageSource = new StaticMessageSource();
        staticMessageSource.addMessage("javax.validation.constraints.NotNull.message", Locale.getDefault(), "字段不能为空");
        //MessageSourceResourceBundleLocator 对 messageSource 包装
        MessageSourceResourceBundleLocator locator = new MessageSourceResourceBundleLocator(staticMessageSource);
        ResourceBundleMessageInterpolator interpolator = new ResourceBundleMessageInterpolator(locator);
        // 定义 localValidatorFactoryBeanDefinition 并设置对应属性
        AbstractBeanDefinition localValidatorFactoryBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(LocalValidatorFactoryBean.class)
                .addPropertyValue("messageInterpolator", interpolator).getBeanDefinition();
        // 定义 MethodValidationPostProcessor 并设置对应属性
        AbstractBeanDefinition processorDefinition = BeanDefinitionBuilder.genericBeanDefinition(MethodValidationPostProcessor.class)
                .addPropertyReference("validator", "localValidatorFactoryBean").getBeanDefinition();
        //注册
        context.registerBeanDefinition("localValidatorFactoryBean", localValidatorFactoryBeanDefinition);
        context.registerBeanDefinition("processor", processorDefinition);
        context.register(SpringValidateTest.class);
        context.refresh();
        context.getBean(SpringValidateTest.class).test(new Person());

    }

    public void test(@Valid Person person){
        System.out.println(person);
    }

    static class Person{
        @NotNull
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Person{");
            sb.append("username='").append(username).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
