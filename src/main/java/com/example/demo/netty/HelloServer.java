package com.example.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author javal
 * @Description: 实现客户端发送一个请求，服务器会返回 hello netty
 */
public class HelloServer {

    public static void main(String[] args) throws Exception {
        // 定义一对线程组
        // 主线程组，用于接受客户端的连接，但是不做任何处理，跟老板一样，不做事
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组，老板线程组会把任务丢给他，让手下线程组去做任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            // netty服务器的创建，serverBootstrap是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HelloServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            // 监听关闭的channel,设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅的关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
