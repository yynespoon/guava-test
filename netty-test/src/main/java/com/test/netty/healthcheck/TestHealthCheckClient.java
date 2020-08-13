package com.test.netty.healthcheck;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaoyu
 * @since 2020-08-13 15:06
 */
public class TestHealthCheckClient {

    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup client = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO))
                                    .addLast(new StringEncoder());
                        }
                    });
            ChannelFuture localhost = bootstrap.connect("localhost", 8888).sync();
            service.scheduleWithFixedDelay(() -> {
                localhost.channel().writeAndFlush("aaaaaaaaaaa");
            }, 1, 1, TimeUnit.SECONDS);

            TimeUnit.SECONDS.sleep(10);

            service.shutdownNow();

            localhost.channel().closeFuture().syncUninterruptibly();

            System.out.println("finish");
        } finally {
            client.shutdownGracefully();
        }
    }
}
