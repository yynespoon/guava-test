package com.test.current;

/**
 * @author lixiaoyu
 * @since 2020/3/25
 */
public class TestBinary {

    static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {

        int RUNNING = -1 << 29 | 0;



        System.out.println(Integer.toBinaryString(1 << 29 -1));

        System.out.println(Integer.toBinaryString(1 << (Integer.SIZE - 3) -1));
        System.out.println("-----------" + Integer.toBinaryString(-1 << 29));
        System.out.println(Integer.toBinaryString(0 << 29));
        System.out.println(Integer.toBinaryString(1 << 29));
        System.out.println(Integer.toBinaryString(2 << 29));
        System.out.println(Integer.toBinaryString(3 << 29));

        System.out.println(1 ^ 0);

        System.out.println(tableSizeFor(100));

        System.out.println(1<<31);
        System.out.println(Integer.toBinaryString((1<<31) + (1<<3)));
        System.out.println(((1<<31) + (1<<3)));

        //System.out.println(Integer.numberOfLeadingZeros(1));
    }

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
