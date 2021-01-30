package ink.fujisann.learning.stock.controller;

import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerDelayTask implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
        // 不间断尝试从延时队列获取需要执行的任务
        while (true) {
            DelayTask task = DataController.delayQueue.take();
            log.info("阻塞获取到延时任务");
            // 阻塞获取到延时任务后，执行任务
            ThreadUtil.execute(task);
        }
    }
}
