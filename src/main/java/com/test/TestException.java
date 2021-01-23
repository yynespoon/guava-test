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
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(111);
            }
        });
        Thread thread = new Thread(() -> {
            throw new RuntimeException();
        });
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Thread thread = new Thread(() -> {
                    throw new RuntimeException();
                });
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        System.out.println(e);
                        throw new RuntimeException();
                    }
                });
                thread.start();
            }
        });
        thread.start();


    }
}
