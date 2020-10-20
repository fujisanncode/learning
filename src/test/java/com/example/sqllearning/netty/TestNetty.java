package com.example.sqllearning.netty;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.sqllearning.SqlLearningApplication;
import com.example.sqllearning.netty.client.NettyClient;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 测试netty
 * @author: hulei
 * @create: 2020-06-10 22:09:57
 */
@Slf4j
@RunWith(SpringRunner.class) // 不在junit环境执行单元测试，指定为spring环境， 即执行前初始化spring上下文环境
@SpringBootTest(classes = SqlLearningApplication.class) // 通过指定启动了诶初始化上下文环境
public class TestNetty {
    @Autowired
    NettyClient nettyClient;

    @Test
    public void testSingleMsg() throws InterruptedException {
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                log.info("netty client start");
                nettyClient.start();
                log.info("netty client end");
            }
        });
        t1.start();
        t1.join(); // 等待t1结束，main线程才能结束·····
    }
}
