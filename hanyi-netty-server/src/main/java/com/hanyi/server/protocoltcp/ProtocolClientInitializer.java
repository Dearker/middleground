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
 * @since 2022/7/31 11:16 AM
 */
public class ProtocolClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MessageEncoder()); //加入编码器
        pipeline.addLast(new MessageDecoder()); //加入解码器
        pipeline.addLast(new ProtocolClientHandler());
    }

}
