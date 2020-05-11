package com.example.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.FloatBuffer;
import java.nio.charset.Charset;

/**
 * 自定义助手类
 */
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

        if (httpObject instanceof HttpRequest){
            // 获取channel
            Channel channel = channelHandlerContext.channel();
            // 打印远程端地址
            System.out.println(channel.remoteAddress());
            // 定义发送的数据消息
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello netty~", CharsetUtil.UTF_8);
            // 构建一个http response
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    byteBuf);
            // 为响应设置数据类型和长度
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            //把响应刷到客户端
            channelHandlerContext.writeAndFlush(fullHttpResponse);
        }

    }
}
