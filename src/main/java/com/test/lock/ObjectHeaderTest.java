package com.test.lock;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author lixiaoyu
 * @since 2020-05-16 23:22
 */
public class ObjectHeaderTest {

    @Test
    public void test() {
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
    }
}
