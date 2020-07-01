package com.test.forkstream;

import com.google.common.collect.Lists;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lixiaoyu
 * @since 2020/6/29
 */
public class TestMain {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100).sum();
        StreamForker<Integer> fork = new StreamForker<Integer>(Lists.newArrayList(1, 2, 3, 4, 5).stream())
                .fork("sum", stream -> stream.collect(Collectors.summingInt(Integer::intValue)))
                .fork("avg", stream -> stream.collect(Collectors.averagingInt(Integer::intValue)));

        Results results = fork.getResults();
        System.out.println(results.get("sum").toString());
        System.out.println(results.get("avg").toString());
    }
}
