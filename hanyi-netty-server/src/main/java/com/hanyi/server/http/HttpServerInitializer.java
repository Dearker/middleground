package com.hanyi.server.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * <p>
 * http服务初始，将编解码和处理器等添加到管道中
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/31 9:09 AM
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        //加入一个netty 提供的httpServerCodec codec =>[coder - decoder]
        //HttpServerCodec 说明
        //1. HttpServerCodec 是netty 提供的处理http的 编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2. 增加一个自定义的handler
        pipeline.addLast("HttpServerHandler", new HttpServerHandler());

        System.out.println("ok~~~~");
    }

}
