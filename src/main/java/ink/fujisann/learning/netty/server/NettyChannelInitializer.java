package ink.fujisann.learning.netty.server;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @description: 定义一组管道中的消息处理器
 * @author: hulei
 * @create: 2020-06-10 21:12:43
 */
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline().addLast(new NettyChannelInBoundHandler());
        socketChannel.pipeline().addLast(new IdleStateHandler(5, 6, 7, TimeUnit.SECONDS));// 设定read或者write闲置后触发eventTrigger
    }
}
