package com.test.current;

import com.google.common.collect.ImmutableList;
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
public class TestGetAllFuture {

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
                return 2;
            }
        });

        //test successful as list 不会抛出异常, 执行不成功的会返回 null 放到 list 中
        ListenableFuture<List<Integer>> successfulAsList = Futures.successfulAsList(Lists.newArrayList(submit, submit1));
        System.out.println(successfulAsList.get());

        //test all as list, 若其中有一个失败或者取消则都没有返回, 但是未取消或失败的会正常执行
        TimeUnit.SECONDS.sleep(3);
        //submit1.cancel(true);
        ListenableFuture<List<Integer>> listListenableFuture = Futures.allAsList(Lists.newArrayList(submit, submit1));
        System.out.println(listListenableFuture.get());

        // 按照添加顺序返回
        ImmutableList<ListenableFuture<Integer>> listenableFutures = Futures.inCompletionOrder(Lists.newArrayList(submit1, submit));
            System.out.println("aaaaaaaa " + listenableFutures.get(0).get());
    }
}
