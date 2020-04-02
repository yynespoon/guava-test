package com.test.current;

/**
 * @author lixiaoyu
 * @since 2020/3/25
 */
public class TestBinary {

    public static void main(String[] args) {

        int RUNNING = -1 << 29 | 0;

        System.out.println(Integer.toBinaryString(1 << 29 -1));

        System.out.println(Integer.toBinaryString(1 << (Integer.SIZE - 3) -1));
        System.out.println("-----------" + Integer.toBinaryString(-1 << 29));
        System.out.println(Integer.toBinaryString(0 << 29));
        System.out.println(Integer.toBinaryString(1 << 29));
        System.out.println(Integer.toBinaryString(2 << 29));
        System.out.println(Integer.toBinaryString(3 << 29));
    }
}
