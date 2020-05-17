package com.test.lock;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author lixiaoyu
 * @since 2020-05-17 21:30
 */
public class HeavyweightLockTest {

    @Test
    public void test(){
        Object lock = new Object();

        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                synchronized (lock){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }
}
