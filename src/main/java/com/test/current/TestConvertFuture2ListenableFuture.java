package com.test.current;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaoyu
 * @since 2020/3/24
 */
public class TestConvertFuture2ListenableFuture {

    public static void main(String[] args) {
        // 1
        ListenableFutureTask<Integer> futureTask = ListenableFutureTask.create(new Callable<Integer>() {
            public Integer call() throws Exception {
                return 1;
            }
        });

        // 2
        JdkFutureAdapters.listenInPoolThread(Executors.newSingleThreadExecutor().submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                return 2;
            }
        })).addListener(new Runnable() {
            public void run() {
                System.out.println("finish");
            }
        }, Executors.newSingleThreadExecutor());
    }
}
