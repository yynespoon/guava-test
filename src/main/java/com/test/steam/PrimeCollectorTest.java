package com.test.steam;

import com.test.collectors.PrimeCollector;

import java.util.stream.IntStream;

/**
 * @author lixiaoyu
 * @since 2020/6/18
 */
public class PrimeCollectorTest {

    public static void main(String[] args) {
        System.out.println(IntStream.rangeClosed(2, 100).boxed().collect(new PrimeCollector()));
    }
}
