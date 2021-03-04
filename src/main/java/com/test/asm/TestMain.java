package com.test.asm;

import org.springframework.core.ResolvableType;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.PrintStream;
import java.lang.reflect.Type;

/**
 * @author lixiaoyu
 * @since 2021/3/4
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
//        Class<?> aClass = Class.forName("com.test.asm.TestBean");
//        aClass.getDeclaredMethod("sout").invoke(aClass.newInstance(), null);
        System.out.println(PrintStream.class.getMethod("println", String.class));
    }
}
