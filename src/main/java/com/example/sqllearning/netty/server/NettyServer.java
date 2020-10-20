package com.example.sqllearning.netty.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.sqllearning.netty.properties.NettyServerConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: netty server配置
 * @author: hulei
 * @create: 2020-06-10 20:49:59
 */
@Slf4j
@Component
public class NettyServer {

    @Autowired
    private NettyServerConfig nettyServerConfig;

    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap().group(new NioEventLoopGroup(), new NioEventLoopGroup()) // 处理正在接入的连接，处理已经接入的连接
            .channel(NioServerSocketChannel.class) // 指定获取连接的channel实现类
            .childOption(ChannelOption.AUTO_READ, Boolean.TRUE).childHandler(new NettyChannelInitializer());
        try {
            // 开放netty server的端口
            ChannelFuture future = serverBootstrap.bind(nettyServerConfig.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
