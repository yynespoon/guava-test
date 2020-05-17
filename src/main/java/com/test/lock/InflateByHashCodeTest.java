package com.test.lock;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author lixiaoyu
 * @since 2020-05-17 18:56
 */
public class InflateByHashCodeTest {

    @Test
    public void test(){
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        o.hashCode();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
