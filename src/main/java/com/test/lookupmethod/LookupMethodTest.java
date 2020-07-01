package com.test.lookupmethod;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class LookupMethodTest {

    public static void main(String[] args) throws Throwable {
        Person person = new Person();
        MethodHandles.Lookup lookup = Person.lookup();
        MethodHandle setUsername = lookup.findSetter(Person.class, "username", String.class);
        setUsername.invokeExact(person, "zhangsan");
        System.out.println(person);

        MethodHandles.Lookup outerLookup = MethodHandles.lookup();
        Method method = Person.class.getDeclaredMethod("setUsername", String.class);
        method.setAccessible(true);
        MethodHandle unreflect = outerLookup.unreflect(method).bindTo(person);
        unreflect.invokeExact("lisi");
        System.out.println(person);
    }
}
