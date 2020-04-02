package com.test.current;

import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author lixiaoyu
 * @since 2020/3/30
 */
public class TestScheduleAsync {

    public static void main(String[] args) {
        //延期执行
        Futures.scheduleAsync(() -> {
            System.out.println(1);
            return null;
        }, Duration.ofSeconds(1), Executors.newSingleThreadScheduledExecutor());
    }
}
