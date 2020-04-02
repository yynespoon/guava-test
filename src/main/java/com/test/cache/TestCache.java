package com.test.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.junit.Test;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * usage: https://github.com/google/guava/wiki/CachesExplained
 *
 * @author lixiaoyu
 * @since 2020/4/2
 */
public class TestCache {

    LoadingCache<String, String> build = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(Duration.ofSeconds(1))
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .maximumSize(100)
            .removalListener(entity -> System.out.println(entity.getKey() + "--" + entity.getValue()))
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return getValue(key);
                }

                @Override
                public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                    SettableFuture<String> future = SettableFuture.create();
                    future.set(oldValue);
                    return future;
                }
            });

    @Test
    public void testCache(){

        while(true){

            for (int i = 0; i < 100; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(()->{
                    System.out.println(build.getUnchecked(String.valueOf(new Random().nextInt(10000))));
                }).start();

            }
        }
    }

    @Test
    public void testRefresh(){
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(build.getUnchecked("111"));
            //if exist, replace and call remove listener
            build.refresh("111");
        }
    }

    public String getValue(String key){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return key;
    }
}
