package com.test.spring.databing;

import com.google.common.collect.Maps;
import com.test.spring.bean.Person;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.Arrays;
import java.util.Map;

/**
 * @author lixiaoyu
 * @since 2020-08-06 11:23
 */
public class SpringDataBindingTest {

    public static void main(String[] args) {

        Person person = new Person();

        DataBinder dataBinder = new DataBinder(person, "person");

        Map<String,Object> paramMap = Maps.newHashMap();
        //1. 允许绑定的属性, 若设置允许绑定或者不允许绑定字段的时候会将不合法的字段放到 bindingResult.suppressedFields中
//        dataBinder.setAllowedFields("username");
//        dataBinder.setDisallowedFields("age");
//        paramMap.put("age", 18);
//        paramMap.put("username", "zhangsan");

        //2. 嵌套属性中如果不存在对应的属性值则抛出异常 默认为忽略
//        dataBinder.setIgnoreInvalidFields(false);
//        paramMap.put("beanFactory.username", "zhangsan");

        //3. 如果被装配的实例中没有该属性是否抛出异常 默认忽略
//        dataBinder.setIgnoreUnknownFields(false);
//        paramMap.put("gender", "male");

        //4. 如果存在嵌套若路径上对象不存在是否自动创建 配合 IgnoreInvalidFields = false 会抛出异常
//        dataBinder.setAutoGrowNestedPaths(false);
//        paramMap.put("beanFactory.allowBeanDefinitionOverriding", "false");

        //5. 若设置属性不存在的时候会将异常信息放到 Errors 中 不会抛出异常
        //dataBinder.setRequiredFields("username");

        PropertyValues propertyValues = new MutablePropertyValues(paramMap);

        dataBinder.bind(propertyValues);

        BindingResult bindingResult = dataBinder.getBindingResult();
        Arrays.stream(bindingResult.getSuppressedFields()).forEach(System.out::println);
        System.out.println(bindingResult);

        System.out.println(person);
    }
}
