package com.test.encrypt;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixiaoyu
 * @since 2020/6/12
 */
public class CipherService {

    private AtomicInteger id = new AtomicInteger(0);

    private Map<Integer, CipherResult> cipherResultMap = Maps.newConcurrentMap();

    public CipherResult getCipherResult(Integer id) {
        return cipherResultMap.get(id);
    }

    public int setCipherResult(CipherResult cipherResult) {
        int id = this.id.incrementAndGet();
        cipherResultMap.put(id, cipherResult);
        return id;
    }
}
