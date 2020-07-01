package com.test.forkstream;

/**
 * @author lixiaoyu
 * @since 2020/6/29
 */
public interface Results {

    <R> R get(Object key);
}
