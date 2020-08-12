package com.test.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * @author lixiaoyu
 * @since 2020-08-12 00:03
 */
public class Acceptor {

    public static void start() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress("127.0.0.1", 8888));
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT, new AcceptHandler(socketChannel));

        while (true) {
            if(selector.select() == 0){
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys) {
                AcceptHandler attachment = (AcceptHandler) selectionKey.attachment();
                attachment.run();
            }
            selectionKeys.clear();
        }
    }
}
