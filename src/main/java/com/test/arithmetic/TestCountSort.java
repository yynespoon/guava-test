package com.test.arithmetic;

/**
 * @author lixiaoyu
 * @since 2021/1/26
 */
public class TestCountSort {

    public static void main(String[] args) {
        int[] arr = new int[]{2,5,3,0,2,3,0,3};
        // 计数数组
        int[] countArr = new int[6];
        int[] sortedArr = new int[arr.length];

        for (int i : arr) {
            countArr[i % countArr.length] += 1;
        }

        for (int i = 1; i < countArr.length; i++) {
            countArr[i] = countArr[i - 1] + countArr[i];
        }

        // 为了保证稳定性要从后往前遍历
        for (int i = arr.length - 1; i >= 0 ; i--) {
            int index = countArr[arr[i]];
            countArr[arr[i]] -= 1;
            sortedArr[index - 1] = arr[i];
        }

        for (int i : sortedArr) {
            System.out.printf(i + " ");
        }

    }
}
