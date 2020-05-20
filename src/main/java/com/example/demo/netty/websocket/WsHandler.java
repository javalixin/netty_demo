package com.example.demo.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import sun.util.resources.LocaleData;

import java.time.LocalDateTime;

/**
 * @ClassName: WsHandler
 * @Description: 自定义处理消息的handler
 * TextWebSocketFrame websocket 处理文本的对象，是消息的载体
 * @Date: 2020/5/20 10:58
 * @Author: javal
 * @Version: 1.0
 */
public class WsHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 用于存放客户端的channel
     */
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        /*客户端传入的消息*/
        String text = textWebSocketFrame.text();
        System.out.println("接收到的信息 : " + text);
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("服务端接收到的信息 : "  + text + " ==== Time : " + LocalDateTime.now()));
        }
        /*作用同上*/
        /*channels.writeAndFlush(new TextWebSocketFrame("服务端接收到的信息 : "  + text + "Time : " + LocalDateTime.now()));*/

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.add(channel);
        /*super.handlerAdded(ctx);*/
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("asShortText : " + ctx.channel().id().asShortText());
        System.out.println("asLongText : " + ctx.channel().id().asLongText());
        /*super.handlerRemoved(ctx);*/
    }
}
