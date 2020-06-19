package com.test.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @author lixiaoyu
 * @since 2020/6/19
 */
public class CalculateRecursiveTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 1000;

    private int start;

    private int length;

    private int[] numbers;

    public CalculateRecursiveTask(int start, int length, int[] numbers) {
        this.start = start;
        this.length = length;
        this.numbers = numbers;
    }

    @Override
    protected Integer compute() {
        if(length < THRESHOLD){
            return calculate();
        }
        CalculateRecursiveTask left = new CalculateRecursiveTask(start, length/2, numbers);
        left.fork();
        CalculateRecursiveTask right = new CalculateRecursiveTask(start + length/2, length - length/2, numbers);
        return right.compute() + left.join();
    }

    private int calculate(){
        int num = 0;
        for (int i = start; i < start + length; i++) {
            num += numbers[i];
        }
        return num;
    }
}
