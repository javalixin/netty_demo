package com.example.demo.netty.websocket;

import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName: WsServerInitializer
 * @Description: websocket自处理器Initializer
 * @Date: 2020/5/20 10:41
 * @Author: javal
 * @Version: 1.0
 */
public class WsServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        /*websocket 基于HTTP协议的编解码*/
        pipeline.addLast(new HttpServerCodec())
                /*对写大数据流的支持*/
                .addLast(new ChunkedWriteHandler())
                /*HttpObjectAggregator为对HttpMessage的聚合，
                 * 在netty的编程中几乎都会使用到该handler
                 **/
                .addLast(new HttpObjectAggregator(1024 * 64))
                /*websocket服务端处理的协议，用于指定客户端访问的路由: /ws
                 * 此handler会处理握手动作：handshaking(close,ping,pong)ping+pong=心跳
                 * websocket都是以frames进行传输的，不同的数据类型frames的类型也不同
                 * */
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                /*添加自定义的助手类*/
                .addLast(new WsHandler());


    }
}
