package com.test.current;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;

/**
 * @author lixiaoyu
 * @since 2020/3/30
 */
public class TestCatchingException {

    static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void main(String[] args) {
        ListenableFuture<Object> submit = service.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            throw new RuntimeException();
        });

        try {
            System.out.println(Futures.catching(submit, RuntimeException.class, ex -> Thread.currentThread().getName(), MoreExecutors.directExecutor()).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
