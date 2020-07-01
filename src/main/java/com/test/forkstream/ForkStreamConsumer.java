package com.test.forkstream;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author lixiaoyu
 * @since 2020/6/29
 */
public class ForkStreamConsumer<T> implements Results, Consumer<T> {

    public static final Object END_OF_STREAM = new Object();

    private final List<BlockingDeque<T>> queues;

    private final Map<Object, Future<?>> actions;

    public ForkStreamConsumer(List<BlockingDeque<T>> queues, Map<Object, Future<?>> actions) {
        this.queues = queues;
        this.actions = actions;
    }

    @Override
    public void accept(T t) {
        queues.forEach(q  -> q.add(t));
    }

    @Override
    public <R> R get(Object key) {
        try {
            return ((Future<R>)actions.get(key)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void finish(){
        accept((T)END_OF_STREAM);
    }
}
