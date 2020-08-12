package com.test.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaoyu
 * @since 2020-08-11 18:03
 */
public class NioServer {

    public static void start() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress("localhost", 8888));
        socketChannel.configureBlocking(false);

        ServerSocketChannel socketChannel2 = ServerSocketChannel.open();
        socketChannel2.bind(new InetSocketAddress("localhost", 9999));
        socketChannel2.configureBlocking(false);

        Selector selector = Selector.open();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                socketChannel2.register(selector, SelectionKey.OP_ACCEPT, "server accept channel");
                selector.wakeup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT, "server accept channel");
        while (selector.select() == 0) {
            continue;
        }
        System.out.println("server start up");
        while (true) {
            selector.select(500);


            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (!next.isValid()) {
                    iterator.remove();
                    continue;
                }
                if (next.isAcceptable()) {
                    System.out.println(next.attachment());
                    SelectableChannel channel = next.channel();
                    if (channel instanceof ServerSocketChannel) {
                        ServerSocketChannel readyServerSocketChannel = (ServerSocketChannel) channel;
                        SocketChannel accept = readyServerSocketChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ, "server read channel");
                    }
                }

                if (next.isReadable()) {
                    Object attachment = next.attachment();
                    System.out.println(attachment);
                    SelectableChannel channel = next.channel();
                    if (channel instanceof SocketChannel) {
                        handleReadableData(((SocketChannel) channel));
                        handleResponse((SocketChannel) channel);
                    }
                }
                iterator.remove();
            }
        }
    }

    private static void handleResponse(SocketChannel channel) throws IOException {
        channel.write(ByteBuffer.wrap("i get it".getBytes()));
        channel.close();
    }

    private static void handleReadableData(SocketChannel channel) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        StringBuilder stringBuilder = new StringBuilder();
        while (channel.read(allocate) > 0) {
            allocate.flip();
            while (allocate.hasRemaining()) {
                stringBuilder.append((char) allocate.get());
            }
            allocate.clear();
        }
        System.out.println(stringBuilder.toString());
    }
}
