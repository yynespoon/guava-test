package com.test.forkstream;

import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

/**
 * @author lixiaoyu
 * @since 2020/6/29
 */
public class BlockQueueSplitretor<T> implements Spliterator<T> {

    private final BlockingQueue<T> blockingQueue;

    public BlockQueueSplitretor(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        T t;
        while(true){
            try{
                t = blockingQueue.take();
                break;
            } catch (InterruptedException e){

            }
        }
        if(t != ForkStreamConsumer.END_OF_STREAM){
            action.accept(t);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return 0;
    }
}
