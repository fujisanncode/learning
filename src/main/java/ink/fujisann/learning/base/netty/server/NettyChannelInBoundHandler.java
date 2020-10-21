package ink.fujisann.learning.base.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: netty server管道中，入队处理器，即接收netty client的消息
 * @author: hulei
 * @create: 2020-06-10 21:14:23
 */
@Slf4j
public class NettyChannelInBoundHandler extends ChannelInboundHandlerAdapter {
    // 消息进入管道的处理器
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("接收到消息");
        Channel channel = ctx.channel();
        ByteBuf byteBuf = (ByteBuf)msg;
        int msgLen = byteBuf.readableBytes();
        byte[] msgBytes = new byte[msgLen];
        byteBuf.readBytes(msgBytes);
        log.info("管道读取 {}, channel {}", new String(msgBytes), channel.id());
        ChannelFuture future = channel.closeFuture();
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                log.info("channel 关闭了 {}, {}", channel.id(), channel.isActive());
            }
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        ctx.channel().read();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught");
    }
}
