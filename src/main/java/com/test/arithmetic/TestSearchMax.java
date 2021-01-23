package com.test.arithmetic;

/**
 * @author lixiaoyu
 * @since 2021/1/20
 */
public class TestSearchMax {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 5, 2, 5, 7, 2, 6, 8, 9, 2, 4, 6, 8, 8, 10, 3};
        int value = search(arr, 1, 0, arr.length - 1);
        System.out.println(value);
    }

    public static int search(int[] arr, int num, int start, int end){
        int pivot = arr[end];
        int i = start;
        for (int k = start; k <= end; k++) {
            if(arr[k] <= pivot) {
                int tmp = arr[k];
                arr[k] = arr[i];
                arr[i] = tmp;
                i++;
            }
        }
        if(i == num) {
            return arr[i - 1];
        } else if (num > i) {
            return search(arr, num, i - 1 , end);
        } else {
            return search(arr, num, start, i - 2);
        }
    }

}
