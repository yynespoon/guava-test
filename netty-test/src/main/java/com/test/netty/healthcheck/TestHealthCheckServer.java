package com.test.netty.healthcheck;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

/**
 * @author lixiaoyu
 * @since 2020-08-13 15:06
 */
public class TestHealthCheckServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup master = new NioEventLoopGroup(1);
        NioEventLoopGroup slaver = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(master, slaver)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new StringDecoder())
                                .addLast(new IdleStateHandler(1, 5, 10))
                                .addLast(new StringEncoder())
                                .addLast(new ChannelInboundHandlerAdapter() {

                                    @Override
                                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                        if(!(evt instanceof IdleStateEvent)){
                                            ctx.fireUserEventTriggered(evt);
                                        }
                                        IdleStateEvent msg = (IdleStateEvent) evt;
                                        if (msg.state() == IdleState.READER_IDLE) {
                                            System.out.println("没有数据读取");
                                        } else if (msg.state() == IdleState.WRITER_IDLE) {
                                            System.out.println("没有数据写入");
                                        } else if (msg == IdleStateEvent.ALL_IDLE_STATE_EVENT) {
                                            System.out.println("无响应");
                                            ctx.channel().close();
                                        }
                                    }
                                });
                    }
                });
        ChannelFuture localhost = serverBootstrap.bind(new InetSocketAddress("localhost", 8888)).sync();
        localhost.channel().closeFuture().syncUninterruptibly();

    }
}
