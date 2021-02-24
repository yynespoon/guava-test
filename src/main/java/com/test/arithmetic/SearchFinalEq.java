package com.test.arithmetic;

/**
 * @author lixiaoyu
 * @since 2021/2/24
 */
public class SearchFinalEq {

    public static void main(String[] args) {
        int[] arr = new int[]{3, 4, 6, 6, 6, 7, 10};
        int index = search(arr, 0, 6, 8);
        System.out.println(index);
    }

    private static int search(int[] arr, int start, int end, int target) {
        while(start <= end) {
            int mid = start + ((end - start) >> 2);
            if(arr[mid] < target) {
                start = mid + 1;
            } else if (arr[mid] > target) {
                end = mid - 1;
            } else {
                if(mid == end || arr[mid + 1] != target) {
                    return mid;
                } else {
                    start = mid + 1;
                }
            }
        }
        return -1;
    }
}
