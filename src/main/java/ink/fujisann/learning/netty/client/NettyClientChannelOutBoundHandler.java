package ink.fujisann.learning.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 客户端出管道处理器
 * @author: hulei
 * @create: 2020-06-11 23:33:11
 */
@Slf4j
public class NettyClientChannelOutBoundHandler extends ChannelOutboundHandlerAdapter {

  @Override
  public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
    // super.close(ctx, promise);
    log.info("netty client close");
  }

  @Override
  public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
    // super.deregister(ctx, promise);
    log.info("netty client deregister");
  }

  @Override
  public void read(ChannelHandlerContext ctx) throws Exception {
    // super.read(ctx);
    log.info("netty client read");
  }

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    // super.write(ctx, msg, promise);
    log.info("netty client write");
  }

  @Override
  public void flush(ChannelHandlerContext ctx) throws Exception {
    // super.flush(ctx);
    log.info("netty client flush");
  }
}
