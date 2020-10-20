package ink.fujisann.learning.netty.client;

import ink.fujisann.learning.netty.properties.NettyProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: netty client
 * @author: hulei
 * @create: 2020-06-10 21:33:39
 */
@Slf4j
@Component // 必须注入容器，其中注入成员变量才能生效
public class NettyClient {

    @Autowired
    private NettyProperty nettyProperty;

    public void start() {
        // 配置netty client
        Bootstrap nettyClient = new Bootstrap().group(new NioEventLoopGroup()) // 用于处理socket连接
            .channel(NioSocketChannel.class) // 指定获取连接的channel实现类
            .handler(new NettyClientChannelInBoundHandler());
        try {
            ChannelFuture future = // 连接到netty server，建立socket长连接
                nettyClient.connect(nettyProperty.getServer().getHost(), nettyProperty.getServer().getPort()).sync();
            log.info("准备发送消息给netty server");
            ByteBuf byteBuf = Unpooled.wrappedBuffer("hello, netty server, i am netty client".getBytes());// 消息必须用byteBuf发送
            future.channel().writeAndFlush(byteBuf); // 写消息到netty server
            log.info("消息发送完毕");
            future.channel().closeFuture().sync(); // 等待连接被关闭
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
