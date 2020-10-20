package com.example.sqllearning.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @description: 处理器初始化
 * @author: hulei
 * @create: 2020-06-11 23:33:43
 */
public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    socketChannel.pipeline().addLast(new NettyClientChannelInBoundHandler());
    socketChannel.pipeline().addLast(new NettyClientChannelOutBoundHandler());
  }
}
