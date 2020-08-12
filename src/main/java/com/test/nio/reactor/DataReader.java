package com.test.nio.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

/**
 * @author lixiaoyu
 * @since 2020-08-12 00:30
 */
public class DataReader implements Runnable {

    private final Selector selector;

    private boolean available = true;

    public DataReader() throws IOException {
        this.selector = Selector.open();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Selector getSelector() {
        return selector;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && available) {
            try {
                if(selector.select() == 0){
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    ((DataProcessor) selectionKey.attachment()).process();
                }
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
