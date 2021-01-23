package com.test.arithmetic;

/**
 * @author lixiaoyu
 * @since 2021/1/19
 */
public class TestFastSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 5, 2, 5, 7, 2, 6, 8, 9, 2, 4, 6, 8, 8, 10, 3};
        fastSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.printf(i + " ");
        }
    }

    private static void fastSort(int[] arr, int startIndex, int endIndex) {
        if(startIndex >= endIndex){
            return;
        }
        int i = startIndex;
        int pivot = arr[endIndex];
        for (int k = startIndex; k < endIndex + 1; k++) {
            if(arr[k] <= pivot) {
                if(i + 1 <= k) {
                    int tmp = arr[k];
                    arr[k] = arr[i];
                    arr[i] = tmp;
                }
                i ++;
            }
        }
        fastSort(arr, startIndex, i - 2);
        fastSort(arr, i , endIndex );
    }
}
