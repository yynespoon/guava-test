package com.test.collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lixiaoyu
 * @since 2020/3/30
 */
public class TestMultiMap {

    @Test
    public void testArrayListMultiMap(){
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("abc", "111");
        multimap.put("abc", "222");
        multimap.put("abc", "333");
        multimap.put("abc", "444");
        System.out.println(multimap);

        //multimap -> map
        Map<String, Collection<String>> collectionMap = multimap.asMap();
        System.out.println(collectionMap);

        //replace
        List<String> strings = multimap.replaceValues("abc", Lists.newArrayList("aaa", "bbb"));
        System.out.println(strings);
        System.out.println(multimap);

        //remove
        multimap.remove("abc", "aaa");
        System.out.println(multimap);

    }

    @Test
    public void testImmutableListMultimap(){
        ImmutableListMultimap<String, Integer> collect = Lists.newArrayList("111", "222", "333").stream()
                .collect(ImmutableListMultimap.toImmutableListMultimap(s -> s.toUpperCase(), Integer::parseInt));
        System.out.println(collect);
    }
}
