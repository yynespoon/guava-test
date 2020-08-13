package com.test.netty.stick.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author lixiaoyu
 * @since 2020-08-13 00:24
 */
public class TestNettyClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup sender = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(sender)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
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
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    for (int i = 0; i < 10; i++) {
                                        ctx.writeAndFlush("client send msg");
                                    }
                                }
                            });
                        }
                    });

            ChannelFuture localhost = bootstrap.connect("localhost", 8888).sync();
            localhost.channel().closeFuture().syncUninterruptibly();
        } finally {
            sender.shutdownGracefully();
        }
    }
}
