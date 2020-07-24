package com.test.spring.lifecycle.create;

import com.test.spring.bean.Person;
import com.test.spring.bean.PersonFactoryBean;
import com.test.spring.bean.SpringConfig;
import com.test.spring.bean.SpringConfigImport;
import org.junit.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.InputStream;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class CreateBeanTest {

    @Autowired
    private ObjectFactory<Person> objectFactory;

    /**
     * 常规创建方式
     */
    @Test
    public void testGenericXmlCreateBean(){
        ClassPathXmlApplicationContext
                context = new ClassPathXmlApplicationContext("classpath:/spring-config.xml");
        System.out.println(context.getBean(Person.class));
    }

    /**
     * 静态工厂创建方式
     */
    @Test
    public void testStaticFactoryCreateBean(){
        ClassPathXmlApplicationContext
                context = new ClassPathXmlApplicationContext("classpath:/spring-config.xml");
        System.out.println(context.getBean("person2"));
    }

    /**
     * 静态方法创建
     */
    @Test
    public void testStaticMethodCreateBean(){
        ClassPathXmlApplicationContext
                context = new ClassPathXmlApplicationContext("classpath:/spring-config.xml");
        System.out.println(context.getBean("person3"));
    }

    /**
     * factory bean 创建方式
     */
    @Test
    public void testFactoryBeanCreateBean(){
        ClassPathXmlApplicationContext
                context = new ClassPathXmlApplicationContext("classpath:/spring-config.xml");
        System.out.println(context.getBean("person4"));
    }

    /**
     * 配置文件
     */
    @Test
    public void testConfigurationCreateBean(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.register(SpringConfig.class);
        configApplicationContext.refresh();
        System.out.println(configApplicationContext.getBean("person5"));
    }

    /**
     * application 注入
     */
    @Test
    public void testRegisterCreateBean(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.register(Person.class);
        configApplicationContext.refresh();
        System.out.println(configApplicationContext.getBean(Person.class));
    }

    /**
     * 底层容器注入
     */
    @Test
    public void testRegisterFactoryCreateBean(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.getBeanFactory().registerSingleton("person7", new Person());
        configApplicationContext.refresh();
        System.out.println(configApplicationContext.getBean("person7"));
        System.out.println(configApplicationContext.getBean("person7", Person.class).getBeanFactory());
    }

    /**
     * 扫描导入
     */
    @Test
    public void testComponentScanCreateBean(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.scan("com.test.spring");
        configApplicationContext.refresh();
        System.out.println(configApplicationContext.getBean(Person.class));
    }

    /**
     * import 导入
     */
    @Test
    public void testImportInjectCreateBean(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.register(SpringConfigImport.class);
        configApplicationContext.refresh();
        System.out.println(configApplicationContext.getBean(Person.class));
    }

    /**
     * 直接构建 beanDefinition
     */
    @Test
    public void testBuildBeanDefinitionCreateBean(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();
        BeanDefinitionReaderUtils.registerWithGeneratedName(BeanDefinitionBuilder.genericBeanDefinition(Person.class).getBeanDefinition(), configApplicationContext);
        configApplicationContext.refresh();
        System.out.println(configApplicationContext.getBean(Person.class));
    }

    /**
     * 通过工厂创建
     */
    @Test
    public void testCreateBean(){
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.getBeanFactory().createBean(Person.class);
        configApplicationContext.refresh();
        System.out.println(configApplicationContext.getBean(Person.class));
    }

    /**
     * SPI
     * @throws Exception
     */
    @Test
    public void testServiceLoaderCreateBean() throws Exception {
        ClassPathXmlApplicationContext
                context = new ClassPathXmlApplicationContext("classpath:/spring-config.xml");
        ServiceLoader person13 = context.getBean("person13", ServiceLoader.class);
        System.out.println(((PersonFactoryBean)person13.iterator().next()).getObject().getBeanFactory());

    }

    /**
     * writable method
     */
    @Test
    public void testCreateBeanByProperties(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(factory);
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/person.properties");
        Resource resource = new InputStreamResource(resourceAsStream);
        EncodedResource encodedResource = new EncodedResource(resource, "GBK");
        reader.loadBeanDefinitions(encodedResource);
        System.out.println(factory.getBean(Person.class));
    }

    @Test
    public void testObjectFactory(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CreateBeanTest.class);
        context.register(Person.class);

        context.refresh();

        ObjectFactory<Person> objectFactory = context.getBean(CreateBeanTest.class).objectFactory;
        System.out.println(objectFactory.getObject());
    }
}
