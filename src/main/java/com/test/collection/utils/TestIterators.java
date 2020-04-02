package com.test.collection.utils;

import com.google.common.collect.*;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author lixiaoyu
 * @since 2020/4/1
 */
public class TestIterators {

    List<String> list1 = Lists.newArrayList("111", "222", "333", "333");
    Set<String> set1 = Sets.newHashSet("aaa", "bbb", "ccc");

    @Test
    public void testIterators(){
        //list1 merge set1
        Iterators.addAll(list1, set1.iterator());
        System.out.println(list1);
        //foreach condition
        //all
        System.out.println(Iterators.all(list1.iterator(), element -> element.length() > 1));
        //any
        System.out.println(Iterators.any(list1.iterator(), element -> element.length() > 1));
        //size
        System.out.println(Iterators.size(set1.iterator()));
        //trans
        System.out.println(Lists.newArrayList(Iterators.transform(list1.iterator(), element -> element += 1)));
        //concat
        System.out.println(Sets.newHashSet(Iterators.concat(set1.iterator(), list1.iterator())));
        //foreach and remove
        ArrayList<String> list2 = Lists.newArrayList(list1);
        Lists.newArrayList(Iterators.consumingIterator(list2.iterator()));
        System.out.println(list2);
        //contain
        System.out.println(Iterators.contains(list1.iterator(), "ccc"));
        //cycle iterator
        Iterators.cycle("abc", "123");
        //collection equals
        //true
        System.out.println(Iterators.elementsEqual(Lists.newArrayList("111", "111").iterator(), Sets.newHashSet("111").iterator()));
        //filter
        System.out.println(Lists.newArrayList(Iterators.filter(list1.iterator(), element -> element.startsWith("3"))));
        //get one, return default value if not exist
        //Iterators.getOnlyElement(list1.iterator(), "default");
        //get first, return default value if not exist
        String aDefault = Iterators.find(list1.iterator(), element -> element.length() > 0, "default");
        //count element in collection
        Iterators.frequency(list1.iterator(), "333");
        //sub collection
        System.out.println(Lists.newArrayList(Iterators.paddedPartition(list1.iterator(), 3)));
        //sub collection, size more than length, add null;
        System.out.println(Lists.newArrayList(Iterators.partition(list1.iterator(), 10)));
        //only get ,not call next()
        Iterators.peekingIterator(Iterators.forArray("abc", "123")).peek();
        Iterators.mergeSorted(Lists.newArrayList(list1.iterator(), set1.iterator()), String::compareTo);
        //排列组合
        for (List<String> permutation : Collections2.orderedPermutations(list1)) {
            for (String s : permutation) {
                System.out.print(s + "\t");
            }
            System.out.println();
        }
    }
}
