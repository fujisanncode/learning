package ink.fujisann.learning.code.controller.hello;

import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

// 异步方法不能和调用异步方法写在同一个类中，因为异步的原理是多线程
@Slf4j
@Component
public class FutureAsync {

    // 同步任务
    public String jobOne() throws InterruptedException {
        Thread.sleep(1000);
        log.info("同步任务1执行完成");
        return "同步任务1执行完成";
    }

    // 异步任务，只能返回void，或者Future
    @Async
    public Future<String> jobTwo() throws InterruptedException {
        log.info("异步任务2starting...");
        Thread.sleep(5000);
        log.info("异步任务2 执行完成");
        // 返回future对象，通过future对异步方法执行进行回调
        return new AsyncResult<String>("异步任务2执行完成");
    }

    // 异步任务
    @Async
    public Future<String> jobThree() throws InterruptedException {
        log.info("异步任务3 starting...");
        Thread.sleep(5000);
        log.info("异步任务3执行完成");
        // 返回future对象，通过future对异步方法执行进行回调
        return new AsyncResult<String>("异步任务3执行完成");
    }
}
