package com.hanyi.server.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/31 11:08 AM
 */
public class ProtocolServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器
        pipeline.addLast(new MessageDecoder());
        //编码器
        pipeline.addLast(new MessageEncoder());
        //处理器
        pipeline.addLast(new ProtocolServerHandler());
    }

}
