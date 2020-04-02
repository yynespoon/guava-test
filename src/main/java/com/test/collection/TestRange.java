package com.test.collection;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.Set;

/**
 * @author lixiaoyu
 * @since 2020/3/31
 *
 * 区间操作 对于状态的映射可以考虑使用 range
 *
 * usage: https://guava.dev/releases/snapshot/api/docs/com/google/common/collect/Range.html#canonical-com.google.common.collect.DiscreteDomain-
 *
 * A RangeSet describes a set of disconnected, nonempty ranges.
 * When adding a range to a mutable RangeSet, any connected ranges are merged together,
 * and empty ranges are ignored
 */
public class TestRange {

    @Test
    public void testRange(){
        //(-∞..+∞)
        System.out.println(Range.all());
        //[100..+∞)
        System.out.println(Range.atLeast(100));
        //(-∞..100]
        System.out.println(Range.atMost(100));
        //contain
        System.out.println(Range.atLeast(100).contains(99));
        //eq atLeast/atMost
        System.out.println(Range.downTo(100, BoundType.CLOSED));
        System.out.println(Range.upTo(100, BoundType.OPEN));
        //区间内的补集
        System.out.println(Range.closed(1, 2).gap(Range.closedOpen(6, 7)));
        //最大区间
        System.out.println(Range.closed(1, 2).span(Range.closedOpen(6, 7)));
        //交集
        System.out.println(Range.closed(1,4).intersection(Range.closed(2, 8)));
        //是否有交集
        System.out.println(Range.closed(1,4).isConnected(Range.closed(2, 8)));
        //子集
        System.out.println(Range.closed(1,4).encloses(Range.closed(1, 3)));
        //是否是空集
        System.out.println(Range.openClosed(1,1).isEmpty());
        //右区间是开区间还是闭区间 无穷会抛异常
        System.out.println(Range.open(1, 2).upperBoundType());
        //右区间的值 无穷会抛异常
        System.out.println(Range.open(1, 2).upperEndpoint());
        //是否有有界右区间
        System.out.println(Range.atLeast(100).hasUpperBound());
        //返回仅包含该值的区间
        System.out.println(Range.singleton(1000));

    }

    @Test
    public void testRangeSet(){
        TreeRangeSet rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 2));
        rangeSet.add(Range.open(3, 4));
        rangeSet.add(Range.openClosed(5, 6));
        rangeSet.add(Range.closedOpen(6, 7));
        System.out.println(rangeSet);
        //contain
        System.out.println(rangeSet.contains(6));
        //not contain
        System.out.println(rangeSet.contains(10));
        //rangeSet 倒序
        Set<Range> descendingRangeSets = rangeSet.asDescendingSetOfRanges();
        System.out.println(descendingRangeSets);
        //rangeSet 正序
        Set<Range> rangeSets = rangeSet.asRanges();
        System.out.println(rangeSets);
    }

    @Test
    public void testRangeMap(){
        TreeRangeMap rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1,5), "abc");
        System.out.println(rangeMap);
        //值相同并且range有交集则合并
        rangeMap.putCoalescing(Range.closed(5, 6), "abc");
        System.out.println(rangeMap);
        System.out.println(rangeMap.get(3));
        //取子区间的map
        System.out.println(rangeMap.subRangeMap(Range.closed(3, 10)));
        System.out.println(rangeMap.subRangeMap(Range.closed(-1, 10)));
    }
}
