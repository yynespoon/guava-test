package com.test.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author lixiaoyu
 * @since 2020-08-12 00:43
 */
public class DataProcessor {

    public static final String OP_READ = "READ";

    public static final String OP_WRITE = "WRITE";

    private String currentOptional;

    private final SelectionKey selectionKey;

    public DataProcessor(String optional, SelectionKey selectionKey) {
        this.currentOptional = optional;
        this.selectionKey = selectionKey;
    }

    public void process() throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        if (OP_READ == currentOptional) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            StringBuilder stringBuilder = new StringBuilder();
            while(channel.read(byteBuffer) > 0){
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()){
                    stringBuilder.append((char) byteBuffer.get());
                }
                byteBuffer.clear();
            }
            System.out.println(stringBuilder.toString());
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            this.currentOptional = OP_WRITE;
            selectionKey.selector().wakeup();
        } else if (OP_WRITE == currentOptional) {
            channel.write(ByteBuffer.wrap("response".getBytes()));
            channel.close();
        } else {
            // do nothing
        }
    }
}
