package com.test.netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Iterator;

/**
 * @author lixiaoyu
 * @since 2020-08-13 13:30
 */
public class TestChatServer {

    public static void main(String[] args) throws InterruptedException, IOException {
        NioEventLoopGroup master = new NioEventLoopGroup(1);
        NioEventLoopGroup slaver = new NioEventLoopGroup(1);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ServerBootstrap channel = serverBootstrap.group(master, slaver)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, false, true, Delimiters.lineDelimiter()))
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new CustomizeHandler());
                    }
                });

        ChannelFuture localhost = channel.bind(new InetSocketAddress("localhost", 8888)).sync();
        localhost.channel().closeFuture().syncUninterruptibly();
    }

    static class CustomizeHandler extends SimpleChannelInboundHandler<String> {

        static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("1");
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("2");
            channelGroup.writeAndFlush(ctx.channel().remoteAddress() + "上线");
            channelGroup.add(ctx.channel());
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("3");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("4");
            Iterator<Channel> iterator = channelGroup.iterator();
            while (iterator.hasNext()) {
                Channel next = iterator.next();
                if (next != ctx.channel()) {
                    next.writeAndFlush(msg);
                }
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("5");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("6");
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            System.out.println("7");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println(cause);
        }
    }
}
