package com.test.arithmetic;

/**
 * @author lixiaoyu
 * @since 2021/1/26
 */
public class TestRadixSort {

    public static void main(String[] args) {
        String[] arr = new String[]{"40656676048", "60475858030", "18018428772", "46103545979", "58297745934", "24105942817", "66294800392", "39712477077", "84465837774", "28999146072"};
        // 按列进行 11 次排序
        for (int i = 10; i >= 0; i--) {
            radixSort(arr, i);
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    private static void radixSort(String[] arr, int i) {
        long[][] sortArr = new long[arr.length][2];
        for (int j = 0; j < sortArr.length; j++) {
            sortArr[j][0] = Long.parseLong(String.valueOf(arr[j].charAt(i)));
            sortArr[j][1] = Long.parseLong(arr[j]);
        }

        divide(sortArr, 0, arr.length - 1);
        for (int j = 0; j < arr.length; j++) {
            arr[j] = String.valueOf(sortArr[j][1]);
        }
    }

    private static void divide(long[][] sortArr, int start, int end) {
        if(start >= end) {
            return;
        }
        int pivot = (start + end) / 2;
        divide(sortArr, start, pivot);
        divide(sortArr, pivot + 1, end);
        merge(sortArr, start, pivot, end);
    }

    private static void merge(long[][] sortArr, int start, int pivot, int end) {
        long tmp[][] = new long[end - start + 1][2];
        int index = 0;
        int i = start;
        int j = pivot + 1;
        for (; i <= pivot && j <= end ;) {
            if(sortArr[i][0] <= sortArr[j][0]) {
                tmp[index ++] = sortArr[i ++];
            } else {
                tmp[index ++] = sortArr[j ++];
            }
        }
        if(i <= pivot) {
            System.arraycopy(sortArr, i, tmp, index, tmp.length - index);
        }

        if(j <= end) {
            System.arraycopy(sortArr, j, tmp, index, tmp.length - index);
        }
        System.arraycopy(tmp, 0, sortArr, start, tmp.length);
    }


}
