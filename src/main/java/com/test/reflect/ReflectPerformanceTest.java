package com.test.reflect;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * @author lixiaoyu
 * @since 2020/6/30
 */
public class ReflectPerformanceTest {

    @Test
    public void testPerformance() throws Throwable {
        Method targe = ReflectPerformanceTest.class.getMethod("target", Object.class);
        targe.setAccessible(true);
        Object o = new Object();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000_000; i++) {
            targe.invoke(null, o);
        }
        System.out.println(System.currentTimeMillis() - start);


        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle target = lookup.findStatic(ReflectPerformanceTest.class, "target", MethodType.methodType(void.class, Object.class));
        long lookupStart = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000_000; i++) {
            target.invokeExact(o);
        }

        System.out.println(System.currentTimeMillis() - lookupStart);
    }

    public static void target(Object o){

    }
}
