package com.test.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixiaoyu
 * @since 2020/4/28
 */
public class TestCellCount {

    private Unsafe unsafe;

    private static CountDownLatch countDownLatch = new CountDownLatch(10000);

    private static CountDownLatch finish = new CountDownLatch(10000);

    private static AtomicInteger integer = new AtomicInteger(0);

    private volatile int count = 0;

    private volatile int cellBusy = 0;

    private CellCount[] cellCounts = new CellCount[8];

    private long countOffset;

    private long cellCountValueOffset;

    private long cellBusyOffset;

    class CellCount {
        volatile int count = 0;
        CellCount(int count){
            this.count = count;
        }
    }

    {
        try {
            Field unsafeField = AtomicInteger.class.getDeclaredField("unsafe");
            unsafeField.setAccessible(true);
            this.unsafe = (Unsafe) unsafeField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            countOffset = unsafe.objectFieldOffset(TestCellCount.class.getDeclaredField("count"));
            cellCountValueOffset = unsafe.objectFieldOffset(CellCount.class.getDeclaredField("count"));
            cellBusyOffset = unsafe.objectFieldOffset(TestCellCount.class.getDeclaredField("cellBusy"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestCellCount testCellCount = new TestCellCount();
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 100; j++) {
                    testCellCount.add();
                }
                finish.countDown();
            }).start();
            countDownLatch.countDown();
        }
        long start = System.currentTimeMillis();
        try {
            finish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("time use :" + (System.currentTimeMillis() - start));
        System.out.println(testCellCount.size());


        //-----------------------atomicInteger-----------------------

        countDownLatch = new CountDownLatch(10000);
        finish = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 100; j++) {
                    integer.incrementAndGet();
                }
                finish.countDown();
            }).start();
            countDownLatch.countDown();
        }
        start = System.currentTimeMillis();
        try {
            finish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("atomic time use :" + (System.currentTimeMillis() - start));
        System.out.println(testCellCount.size());
    }

    static class ThreadLocalUtils {
        private static Random random = new Random();

        private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> random.nextInt(20));

        public static void reset() {
            threadLocal.set(random.nextInt(20));
        }

        public static int get(){
            return threadLocal.get();
        }
    }

    public void add() {
        while (true) {
            CellCount cellCount;
            int CellCountIndex;
            if (unsafe.compareAndSwapInt(this, countOffset, count, count + 1)) {
                return;
            } else if ((cellCount = cellCounts[CellCountIndex = (ThreadLocalUtils.get() & cellCounts.length - 1)]) != null) {
                if(unsafe.compareAndSwapInt(cellCount, cellCountValueOffset, cellCount.count, cellCount.count + 1)){
                    return;
                }
            } else if (cellBusy == 0 && unsafe.compareAndSwapInt(this, cellBusyOffset, 0, 1)){
                if(cellCounts[CellCountIndex] == null){
                    cellCounts[CellCountIndex] = new CellCount(1);
                }
                cellBusy = 0;
                return;
            }
            if (unsafe.compareAndSwapInt(this, countOffset, count, count + 1)) {
                return;
            } else {
                ThreadLocalUtils.reset();
            }

        }
    }

    public int size(){
        int sum = count;
        for (int i = 0; i < cellCounts.length; i++) {
            if(cellCounts[i] == null){
                continue;
            }
            System.out.println("cellcount num " + i +":" + cellCounts[i].count);
            sum += cellCounts[i].count;
        }
        return sum;
    }
}
