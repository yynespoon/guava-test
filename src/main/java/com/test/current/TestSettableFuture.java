package com.test.current;

import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaoyu
 * @since 2020/3/24
 */
public class TestSettableFuture {

    public static void main(String[] args) {
        final SettableFuture<Integer> settableFuture = SettableFuture.create();
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                settableFuture.set(1);
            }
        });
        try {
            System.out.println(settableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
