package com.test.collection;

import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author lixiaoyu
 * @since 2021/3/4
 */
public class TestConcurrentSkipList {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            map.put(random.nextInt(500), random.nextInt(500));
        }
        map.put(1,1);
    }
}
