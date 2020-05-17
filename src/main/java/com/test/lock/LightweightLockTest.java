package com.test.lock;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author lixiaoyu
 * @since 2020-05-17 17:03
 */
public class LightweightLockTest {

    @Test
    public void test(){
        Object o = new Object();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
