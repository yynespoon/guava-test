package com.test.forkstream;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author lixiaoyu
 * @since 2020/6/29
 */
public class StreamForker<T> {

    private final Stream<T> stream;
    private final Map<Object, Function<Stream<T>, ?>> forks = new HashMap<>();

    public StreamForker(Stream<T> stream) {
        this.stream = stream;
    }

    public StreamForker<T> fork(Object key, Function<Stream<T>, ?> f){
        forks.put(key, f);
        return this;
    }

    public Results getResults(){
        ForkStreamConsumer<T> forkStreamConsumer = build();
        try {
            stream.sequential().forEach(forkStreamConsumer);
        } finally {
            forkStreamConsumer.finish();
        }
        return forkStreamConsumer;
    }

    private ForkStreamConsumer<T> build() {
        List<BlockingQueue<T>> queues = new ArrayList<>();
        HashMap<Object, Future<?>> reduce = forks.entrySet().stream().reduce(new HashMap<Object, Future<?>>(),
                (map, e) -> {
                    map.put(e.getKey(), getOptionalResult(queues, e.getValue()));
                    return map;
                }, (m1, m2) -> {
                    m1.putAll(m2);
                    return m1;
                });
        return new ForkStreamConsumer(queues, reduce);
    }

    private Future<?> getOptionalResult(List<BlockingQueue<T>> queues, Function<Stream<T>, ?> value) {
        BlockingQueue<T> queue = new LinkedBlockingDeque<>();
        queues.add(queue);
        Spliterator<T> spliterator = new BlockQueueSplitretor<>(queue);
        Stream<T> stream = StreamSupport.stream(spliterator, false);
        return CompletableFuture.supplyAsync(() -> value.apply(stream));
    }
}
