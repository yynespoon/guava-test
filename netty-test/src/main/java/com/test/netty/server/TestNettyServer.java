package com.test.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author lixiaoyu
 * @since 2020-08-13 00:36
 */
public class TestNettyServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup master = new NioEventLoopGroup(1);
        NioEventLoopGroup slaver = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(master, slaver)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //处理粘包的 inbound handler
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new StringDecoder());
                        //处理粘包的 outbound handler
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                System.out.println(msg);
                                ctx.writeAndFlush("server received");

                            }
                        });
                    }
                });
        ChannelFuture localhost = serverBootstrap.bind(new InetSocketAddress("localhost", 8888)).sync();
        localhost.channel().closeFuture().syncUninterruptibly();
    }
}
