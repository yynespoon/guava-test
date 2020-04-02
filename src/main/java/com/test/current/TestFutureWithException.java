package com.test.current;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaoyu
 * @since 2020/3/25
 */
public class TestFutureWithException {

    static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void main(String[] args) throws Exception {
        ListenableFuture<Integer> submit = service.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(111);
                return 1;
            }
        });
        ListenableFuture<Integer> submit1 = service.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                throw new Exception();
            }
        });


        //test all as list, 若其中有一个失败或者取消则都没有返回, 但是未取消或失败的会正常执行
        TimeUnit.SECONDS.sleep(3);
        //submit1.cancel(true);
        ListenableFuture<List<Integer>> listListenableFuture = Futures.allAsList(Lists.newArrayList(submit, submit1));
        System.out.println(listListenableFuture.get());
    }
}
