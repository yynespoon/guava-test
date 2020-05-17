package com.test.lock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lixiaoyu
 * @since 2020-05-16 22:57
 */
public class SyncAfterJdk16Test {

    @Test
    public void syncTest(){
        Object lock = new Object();
        Lock jucLock = new ReentrantLock();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100000000; i++) {
            synchronized (lock){
                i ++;
            }
        }

        System.out.println("sync time used " + (System.currentTimeMillis() - start));

        long jucStart = System.currentTimeMillis();

        for (int i = 0; i < 100000000; i++) {
            jucLock.lock();
            i ++;
            jucLock.unlock();
        }

        System.out.println("juc time used " + (System.currentTimeMillis() - jucStart));
    }
}
