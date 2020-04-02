package com.test.collection;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import org.junit.Test;

import java.util.Iterator;

/**
 * @author lixiaoyu
 * @since 2020/3/31
 *
 *  _________________________________________________________
 * |Implementation	        Keys behave like...	Values behave like..|
 * |ArrayListMultimap	    HashMap	            ArrayList                   |
 * |HashMultimap	        HashMap	            HashSet                         |
 * |LinkedListMultimap *	LinkedHashMap``*	LinkedList``*|
 * |LinkedHashMultimap**	LinkedHashMap	    LinkedHashSet   |
 * |TreeMultimap	        TreeMap	            TreeSet                         |
 * |ImmutableListMultimap	ImmutableMap	    ImmutableList   |
 * |ImmutableSetMultimap	ImmutableMap	    ImmutableSet    |
 *
 */
public class TestMultiSet {

    @Test
    public void testMultiSet(){
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("aaa", 2);
        multiset.add("aaa", 2);
        multiset.remove("aaa");
        System.out.println(multiset);
        //count sum
        System.out.println(multiset.size());
        //cas
        System.out.println(multiset.setCount("aaa", 5, 10));
        //count aaa
        System.out.println(multiset.count("aaa"));
        //toSet
        System.out.println(multiset.elementSet());
        //entrySet
        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            System.out.println(entry.getElement() + " x " + entry.getCount());
        }
        Iterator iterator = multiset.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
