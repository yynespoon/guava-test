package com.test.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collector;
import java.util.stream.IntStream;

/**
 * @author lixiaoyu
 * @since 2020/6/19
 */
public class ForkJoinTaskTest {

    public static void main(String[] args) throws Exception {
        int[] num = IntStream.rangeClosed(0, 1000000).toArray();
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Integer integer = forkJoinPool.submit(new CalculateRecursiveTask(0, num.length, num)).get();

        int sum = IntStream.rangeClosed(0, 1000000).sum();

        assert integer.intValue() == sum;
    }
}
