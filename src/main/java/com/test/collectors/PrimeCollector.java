package com.test.collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author lixiaoyu
 * @since 2020/6/18
 */
public class PrimeCollector implements Collector<Integer, Map<Boolean, List<Integer>>, List<Integer>> {

    /**
     * init container
     * @return
     */
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> ImmutableMap.of(true, Lists.<Integer>newArrayList(), false, Lists.<Integer>newArrayList());
    }

    /**
     * how to consume each element
     * @return
     */
    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (map, element) -> map.get(isPrime(map.get(true), element)).add(element);
    }

    /**
     * how to combine after split
     * @return
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        };
    }

    /**
     * what to return after combine arithmetic
     * @return
     */
    @Override
    public Function<Map<Boolean, List<Integer>>, List<Integer>> finisher() {
        return map -> map.get(true);
    }

    /**
     * how to optimize
     * @return
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Sets.newHashSet();
    }

    private boolean isPrime(List<Integer> primeList, int i){
        return primeList.stream().noneMatch(num -> i % num == 0);
    }
}
