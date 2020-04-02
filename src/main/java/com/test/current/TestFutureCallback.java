package com.test.current;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author lixiaoyu
 * @since 2020/3/24
 */
public class TestFutureCallback {

    static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    static FutureCallback futureCallback = new FutureCallback() {
        public void onSuccess(@Nullable Object result) {
            System.out.println(Thread.currentThread().getName());
            System.out.println(result);
        }

        public void onFailure(Throwable t) {
            System.out.println(t);
        }
    };

    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread().getName());
        ListenableFuture<Integer> submit = service.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName());
                System.out.println(111);
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
        submit.get();
        // test call back
        Futures.addCallback(submit, futureCallback, MoreExecutors.directExecutor());
        Futures.addCallback(submit1, futureCallback, MoreExecutors.directExecutor());

        //test when all complete
//        ListenableFuture<Object> call = Futures.whenAllComplete(Lists.newArrayList(submit, submit1)).call(new Callable<Object>() {
//            public Object call() throws Exception {
//                System.out.println(123);
//                return null;
//            }
//        }, Executors.newSingleThreadExecutor());
//
//        System.out.println(call.get());

    }

}
