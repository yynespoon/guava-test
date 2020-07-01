package com.test.order;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.List;

/**
 * @author lixiaoyu
 * @since 2020/4/1
 */
public class TestOrder {

    List<Integer> list = Lists.newArrayList(1,3,2,null,5,4,10);

    @Test
    public void test(){
        //onResultOf 用function.apply()之后的结果进行排序
        Ordering<Integer> ordering = Ordering.natural().nullsFirst().onResultOf(element-> element == null ? null : element.toString());
        List<Integer> list = ordering.sortedCopy(this.list);
        System.out.println(list);
        //倒序
        System.out.println(ordering.reverse().sortedCopy(this.list));
        //max
        System.out.println(ordering.max(10,20,100,5));
        //是否是有序的
        System.out.println(ordering.isOrdered(list));
        System.out.println(Ordering.usingToString().nullsFirst().reverse().sortedCopy(list));

        //compound 如果第一个comparator比不出大小则用第二个比
        System.out.println(Ordering.from((String a, String b) -> ComparisonChain.start().compare(a.length(), b.length()).result()).compound(Ordering.usingToString()).sortedCopy(Lists.newArrayList("1", "2", "5", "4", "10")));


    }
}
