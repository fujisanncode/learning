package ink.fujisann.learning.base.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: netty server管道中，入队处理器，即接收netty client的消息
 * @author: hulei
 * @create: 2020-06-10 21:14:23
 */
@Slf4j
public class NettyClientChannelInBoundHandler extends ChannelInboundHandlerAdapter {

  // 消息进入管道的处理器
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    log.info("接收到消息");
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
