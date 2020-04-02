package com.test.collection.utils;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author lixiaoyu
 * @since 2020/4/1
 */
public class TestLists {

    private List<String> list = Lists.newArrayList("111", "222", "333");
    private List<String> list2 = Lists.newArrayList("aaa", "bbb", "ccc");

    @Test
    public void testLists(){
        // [[111, 222], [333]]
        System.out.println(Lists.partition(list, 2));
        //笛卡尔积
        System.out.println(Lists.cartesianProduct(Lists.newArrayList(list, list2)));
        //string->char
        System.out.println(Lists.charactersOf("string"));
    }
}
