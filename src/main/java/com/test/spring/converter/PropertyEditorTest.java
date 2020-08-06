package com.test.spring.converter;

import com.test.spring.bean.Person;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @author lixiaoyu
 * @since 2020-08-06 16:08
 */
public class PropertyEditorTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext();

        AbstractBeanDefinition propertiesContext = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("propertiesContext", "username = zhangsan").getBeanDefinition();

        context.registerBeanDefinition("propertiesContext", propertiesContext);
        context.getBeanFactory().addPropertyEditorRegistrar(new CustomizerPropertiesEditorRegistrar());
        context.refresh();
        System.out.println(context.getBean(Person.class));
    }

    static class CustomizerPropertiesEditorRegistrar implements PropertyEditorRegistrar{

        @Override
        public void registerCustomEditors(PropertyEditorRegistry registry) {
            registry.registerCustomEditor(String.class, "propertiesContext", new PropertiesPropertyEditor());
        }
    }

    static class PropertiesPropertyEditor extends PropertyEditorSupport {

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Properties properties = new Properties();
            try {
                properties.load(new StringReader(text));
                setValue(properties);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
