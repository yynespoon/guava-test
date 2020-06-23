package com.test;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * @author lixiaoyu
 * @since 2020/4/2
 */
public class TestException {

    @Test
    public void testException() {
        try{
            Lists.newArrayList(null, 1,2,3).stream()
                    .peek(element -> element.toString())
                    .forEach(System.out::println);
        } catch (Exception e){
            e.addSuppressed(new RuntimeException("123"));
            throw e;
        }

    }
}
