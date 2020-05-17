package com.test.lock;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author lixiaoyu
 * @since 2020-05-17 16:57
 */
public class HashCodeTest {

    @Test
    public void test() {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        o.hashCode();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
