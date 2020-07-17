package com.test.collection;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import org.junit.Test;

/**
 * @author lixiaoyu
 * @since 2020/3/30
 *
 * key value 都不能相同
 */
public class TestBitMap {

    @Test
    public void testImmutableBiMap(){
        //bean
        ImmutableBiMap.Builder<String, String> builder = ImmutableBiMap.builder();
        builder.put("aaa", "111");
        builder.put("bbb", "222");
        builder.put("ccc", "333");
        ImmutableBiMap<String, String> multimap = builder.build();

        //最多支持5对
        ImmutableBiMap<String, String> multimap1 = ImmutableBiMap.of("aaa", "111", "bbb", "222", "ccc", "333");

        System.out.println(multimap);
        System.out.println(multimap1.inverse());
    }

    @Test
    public void testHashBiMap(){
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("aaa", "111");
        biMap.put("bbb", "222");
        biMap.put("ccc", "333");
        biMap.put("ddd", "444");
        System.out.println(biMap);
        BiMap<String, String> inverseBiMap = biMap.inverse();
        //遍历
        inverseBiMap.forEach((key, value) -> System.out.println(key + value));
        //是否包含键/值
        System.out.println(inverseBiMap.containsKey("aaa"));
        System.out.println(inverseBiMap.containsValue("aaa"));
    }
}
