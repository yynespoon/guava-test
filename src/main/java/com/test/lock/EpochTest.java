package com.test.lock;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;

/**
 * @author lixiaoyu
 * @since 2020-05-17 17:47
 */
public class EpochTest {

    @Test
    public void test(){
        ArrayList<Object> objects = Lists.newArrayList();

        Thread t = new Thread(()->{
            for (int i = 0; i < 61; i++) {
                Object o = new Object();
                objects.add(o);
                synchronized (o){
                    if(i == 0){
                        System.out.println(ClassLayout.parseInstance(o).toPrintable());
                    }
                }
            }
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------第一组对照----------------------------");
        //对照
        Object o = new Object();
        for (int i = 0; i < 61; i++) {
            if( i == 0){
                synchronized (o){
                    System.out.println(ClassLayout.parseInstance(o).toPrintable());
                }
            }
            synchronized (objects.get(i)){
                if(i == 60){
                    System.out.println(ClassLayout.parseInstance(objects.get(i)).toPrintable());
                }
            }
        }

        System.out.println("------------------------------第二组对照----------------------------");

        Thread thread = new Thread(()->{
            //对照
            Object o2 = new Object();
            for (int i = 0; i < 61; i++) {
                if( i == 0){
                    synchronized (o2){
                        System.out.println(ClassLayout.parseInstance(o).toPrintable());
                    }
                }
                synchronized (objects.get(i)){
                    if(i == 60){
                        System.out.println(ClassLayout.parseInstance(objects.get(i)).toPrintable());
                    }
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
