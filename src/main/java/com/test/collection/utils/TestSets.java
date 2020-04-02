package com.test.collection.utils;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

/**
 * @author lixiaoyu
 * @since 2020/4/1
 */
public class TestSets {

    Set<String> set = Sets.newHashSet("111", "222", "333", "555");
    Set<String> set2 = Sets.newHashSet("111", "222", "333", "444");

    @Test
    public void testSets() {
        //交集
        System.out.println(Sets.intersection(set, set2));
        //分成每组两个的子set
        for (Set<String> combination : Sets.combinations(set, 2)) {
            System.out.println(combination);
        }
        //set独有的元素[555]
        System.out.println(Sets.difference(set, set2));
        //set set2各自独有的元素
        System.out.println(Sets.symmetricDifference(set, set2));
        //所有可能的子set
        for (Set<String> strings : Sets.powerSet(set)) {
            System.out.println("--->" + strings);
        }
        //并集
        System.out.println(Sets.union(set, set2));
    }
}
