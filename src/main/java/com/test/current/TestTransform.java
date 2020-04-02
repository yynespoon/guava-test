package com.test.current;

import com.google.common.base.Function;
import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaoyu
 * @since 2020/3/25
 */
public class TestTransform {

    static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void main(String[] args) {
        ListenableFuture<Integer> submit = service.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(111);
                System.out.println(Thread.currentThread().getName());
                return 1;
            }
        });
        ListenableFuture<Integer> submit1 = service.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                //throw new Exception();
                return 2;
            }
        });

        //如果执行结果传递给回调函数是个异步行为
        ListenableFuture<Integer> abc123 = Futures.transformAsync(submit, new AsyncFunction<Integer, Integer>() {
            public ListenableFuture<Integer> apply(@Nullable Integer input) throws Exception {
                System.out.println("-------" + input);
                System.out.println("abc123");
                return Futures.immediateFuture(1);
            }
        }, MoreExecutors.directExecutor());

        System.out.println(Thread.currentThread().getName());

        //如果执行结果传递给回调函数是个同步行为 == Futures.lazyTransform()
        ListenableFuture<Object> abc1231 = Futures.transform(submit, new Function<Integer, Object>() {
            @Nullable
            public Object apply(@Nullable Integer input) {
                System.out.println("-------2--" + input);
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        }, MoreExecutors.directExecutor());
    }
}
