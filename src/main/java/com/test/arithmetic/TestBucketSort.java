package com.test.arithmetic;

/**
 * @author lixiaoyu
 * @since 2021/1/23
 */
public class TestBucketSort {

    public static void main(String[] args) {
        int[] arr = new int[]{22,5,11,41,45,26,29,10,7,8,30,27,42,43,40};
        int[] bucketNum = new int[5];
        int[][] bucket = new int[5][];
        for (int i : arr) {
            int num = bucketNum[i / 10] + 1;
            bucketNum[i / 10] = num;
        }
        for (int i = 0; i < bucketNum.length; i++) {
            bucket[i] = new int[bucketNum[i]];
            bucketNum[i] = 0;
        }
        for (int i = 0; i < arr.length; i++) {
            int index = arr[i] / 10;
            int num = bucketNum[index];
            bucket[index][num] = arr[i];
            bucketNum[index] = num + 1;
        }

        for (int[] ints : bucket) {
            int slot = (0 + ints.length - 1) / 2;
            divide(ints, 0, slot);
            divide(ints, slot + 1, ints.length - 1);
            mergeSort(ints, 0,  slot, ints.length - 1);
        }

        for (int i = 0; i < bucket.length; i++) {
            for (int j = 0; j < bucket[i].length; j++) {
                System.out.printf(bucket[i][j] + " ");
            }
            System.out.println();
        }
        arr = new int[arr.length];
        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            for (int j = 0; j < bucket[i].length; j++) {
                arr[index++] = bucket[i][j];
            }
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.printf(arr[i] + " ");
        }
    }

    private static void divide(int[] arr, int start, int end) {
        if(start >= end) {
            return;
        }

        int slot = (start + end) / 2;
        divide(arr, start, slot);
        divide(arr, slot + 1, end);
        mergeSort(arr, start, slot, end);
    }

    private static void mergeSort(int[] arr, int start, int slot, int end) {
        int[] tmp = new int[end - start + 1];
        int[] lowArr = new int[slot - start + 2];
        int[] highArr = new int[end - slot + 1];

        for (int i = start; i <= slot; i++) {
            lowArr[i - start] = arr[i];
        }
        lowArr[lowArr.length - 1] = Integer.MAX_VALUE;

        for (int i = slot + 1; i <= end; i++) {
            highArr[i - (slot + 1)] = arr[i];
        }
        highArr[highArr.length - 1] = Integer.MAX_VALUE;

        int j = 0, k = 0;
        for (int i = 0; i < tmp.length; i++) {
            if(lowArr[j] <= highArr[k]){
                tmp[i] = lowArr[j++];
            } else {
                tmp[i] = highArr[k++];
            }
        }
        System.arraycopy(tmp, 0, arr, start, tmp.length);
    }
}
