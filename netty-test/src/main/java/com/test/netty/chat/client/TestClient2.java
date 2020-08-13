package com.test.netty.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author lixiaoyu
 * @since 2020-08-13 13:30
 */
public class TestClient2 {

    public static void main(String[] args) throws InterruptedException, IOException {
        NioEventLoopGroup client = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(client)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new StringEncoder())
                                .addLast(new StringDecoder())
                                .addLast(new SimpleChannelInboundHandler<String>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                        System.out.println(msg);
                                    }
                                });
                    }
                });
        ChannelFuture localhost = bootstrap.connect("localhost", 8888).sync();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            localhost.channel().writeAndFlush(bufferedReader.readLine() + "\r\n");
        }
    }
}
