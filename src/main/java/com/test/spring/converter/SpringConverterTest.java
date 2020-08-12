package com.test.spring.converter;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.test.spring.bean.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author lixiaoyu
 * @since 2020-08-06 16:08
 */
public class SpringConverterTest implements BeanDefinitionRegistryPostProcessor {

    @Value("user.name")
    public String username;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        Properties properties = new Properties();
        properties.put("username", "zhangsan");

        AbstractBeanDefinition mapContext = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("mapContext", properties).getBeanDefinition();
        context.registerBeanDefinition("mapContext", mapContext);

        AbstractBeanDefinition converters = BeanDefinitionBuilder.genericBeanDefinition(ConversionServiceFactoryBean.class)
                .addPropertyValue("converters", Sets.newHashSet(new Properties2StringConverter()))
                .getBeanDefinition();

        context.registerBeanDefinition("conversionService", converters);

        context.register(SpringConverterTest.class);
        context.refresh();

        System.out.println(context.getBean(Person.class));
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    static class Properties2StringConverter implements ConditionalGenericConverter{

        @Override
        public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
            return sourceType.getObjectType() == Properties.class &&
                    targetType.getMapKeyTypeDescriptor().getObjectType() == String.class &&
                    targetType.getMapValueTypeDescriptor().getObjectType() == String.class;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(Properties.class, Map.class));
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            Properties properties = (Properties) source;
            return Maps.newHashMap(properties);
        }
    }
}
