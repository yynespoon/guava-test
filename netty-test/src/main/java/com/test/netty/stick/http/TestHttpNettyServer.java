package com.test.netty.stick.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.InetSocketAddress;

/**
 * @author lixiaoyu
 * @since 2020-08-12 20:52
 */
public class TestHttpNettyServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup master = new NioEventLoopGroup(1);
        NioEventLoopGroup slaver = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(master, slaver)
                    .childHandler(new ChannelInitializer<SocketChannel>(){

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                    System.out.println(msg.uri());
                                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                                            Unpooled.wrappedBuffer("finish".getBytes()));
                                    HttpHeaders httpHeaders = response.headers();
                                    httpHeaders.set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
                                    httpHeaders.set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
                                    ctx.writeAndFlush(response);
                                }
                            });
                        }
                    })
                    .channel(NioServerSocketChannel.class);
            ChannelFuture localhost = bootstrap.bind(new InetSocketAddress("localhost", 8888))
                    .sync();
            localhost.syncUninterruptibly();
        } finally {
            master.shutdownGracefully();
            slaver.shutdownGracefully();
        }
    }
}
