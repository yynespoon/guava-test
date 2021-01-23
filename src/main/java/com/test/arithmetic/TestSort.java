package com.test.arithmetic;

import java.util.Arrays;

/**
 * @author lixiaoyu
 * @since 2020/10/15
 */
public class TestSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 5, 2, 5, 7, 2, 6, 8, 9, 2, 4, 6, 8, 8, 10, 3};
        divide(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.printf(i + " ");
        }
    }

    private static void divide(int[] arr, int lowStart, int end) {
        if (lowStart >= end) {
            return;
        }
        int slot = (lowStart + end) / 2;
        divide(arr, slot + 1, end);
        divide(arr, lowStart, slot);
        //归并
        //int[] ints = mergeSort(arr, lowStart, slop, end);
        //归并+哨兵
        int[] ints = sentryMerge(arr, lowStart, slot, end);
        System.arraycopy(ints, 0, arr, lowStart, ints.length);
    }

    private static int[] mergeSort(int[] sort, int lowStart, int slop, int end) {

        int[] tmp = new int[end - lowStart + 1];
        int i = lowStart, j = slop + 1, k = 0;
        for (; i <= slop && j <= end; k++) {
            if (sort[i] <= sort[j]) {
                tmp[k] = sort[i++];
            } else {
                tmp[k] = sort[j++];
            }
        }
        if (k <= tmp.length) {
            if (i <= slop) {
                System.arraycopy(sort, i, tmp, k, tmp.length - k);
            } else {
                System.arraycopy(sort, j, tmp, k, tmp.length - k);
            }
        }
        return tmp;
    }

    private static int[] sentryMerge(int[] sort, int lowStart, int slop, int end){
        int[] temp = new int[end - lowStart + 1];
        int[] lowArr = new int[slop - lowStart + 2];
        int[] highArr = new int[end - (slop + 1) + 2];

        lowArr[lowArr.length -1 ] = Integer.MAX_VALUE;
        highArr[highArr.length - 1] = Integer.MAX_VALUE;

        for (int i = lowStart; i < slop + 1; i++) {
            lowArr[i-lowStart] = sort[i];
        }

        for (int i = slop + 1; i < end + 1; i++) {
            highArr[i - slop - 1] = sort[i];
        }

        int j = 0, k = 0;
        for (int i = 0; i < temp.length; i++) {
            if(lowArr[j] <= highArr[k]) {
                temp[i] = lowArr[j++];
            } else {
                temp[i] = highArr[k++];
            }
        }
        return temp;
    }
}
