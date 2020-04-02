package com.test;

import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * @author lixiaoyu
 * @since 2020/4/2
 */
public class TestException {

    @Test
    public void testException() {
        try {
            Executors.newSingleThreadExecutor().submit(()->{
                throw new RuntimeException();
            });
        } catch (Exception e){
            System.out.println("ex");
        }
    }
}
