package com.test.nio.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixiaoyu
 * @since 2020-08-12 00:06
 */
public class AcceptHandler {

    private static final int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();

    private static final DataReader[] DATA_READERS = new DataReader[2];

    private AtomicInteger count = new AtomicInteger(0);

    private final ServerSocketChannel serverSocketChannel;

    public AcceptHandler(ServerSocketChannel socketChannel) {
        this.serverSocketChannel = socketChannel;
    }

    public void run() throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        int position = count.incrementAndGet() % DATA_READERS.length;
        DataReader dataReader = DATA_READERS[position];
        if(dataReader == null){
            dataReader = new DataReader();
            new Thread(dataReader).start();
        }
        dataReader.setAvailable(false);
        dataReader.getSelector().wakeup();
        SelectionKey register = socketChannel.register(dataReader.getSelector(), SelectionKey.OP_READ);
        // 这地方可以优化 用对象池 暂时先这么写
        register.attach(new DataProcessor(DataProcessor.OP_READ, register));

        dataReader.setAvailable(true);
    }
}
