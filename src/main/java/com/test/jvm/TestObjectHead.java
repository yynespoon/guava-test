package com.test.jvm;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiaoyu
 * @since 2020-05-02 12:52
 */
public class TestObjectHead {

    public static void main(String[] args) {


        List<TestModel> list = new ArrayList<>();

        Thread t = new Thread(()->{
            for (int i = 0; i < 100; i++) {
                TestModel testModel = new TestModel();
                list.add(testModel);
                synchronized (testModel){
                    System.out.println(111);
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 在有 锁竞争 的情况下
         * 同一个 class 锁撤销（未冲突但是不同线程获取）到达阈值 -XX:BiasedLockingBulkRebiasThreshold default 20 次之后
         * 不再升级为轻量级锁而是进行锁重新偏向 即将对象头线程 id 替换
         */

        Thread t2 = new Thread(()->{
            for (int i = 0; i < 100; i++) {
                synchronized (list.get(i)){
                    if(i ==25)
                        System.out.println(ClassLayout.parseInstance(list.get(list.size()-1)).toPrintable());

                    if(i ==99)
                        System.out.println(ClassLayout.parseInstance(list.get(list.size()-1)).toPrintable());
                }
            }
        });
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 在有第三个线程 锁竞争 的情况下
         * 同一个 class 锁偏向（未冲突但是不同线程获取）到达阈值 -XX:BiasedLockingBulkRevokeThreshold default 40 次之后
         * 不再进行锁重偏向 直接升级为轻量级锁
         */

        for (int i = 0; i < 100; i++) {
            synchronized (list.get(i)){
                if(i ==25)
                    System.out.println(ClassLayout.parseInstance(list.get(list.size()-1)).toPrintable());

                if(i ==99)
                    System.out.println(ClassLayout.parseInstance(list.get(list.size()-1)).toPrintable());
            }
        }


    }
}
