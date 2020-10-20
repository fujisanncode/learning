package ink.fujisann.learning.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @description: 测试线程
 * @author: hulei
 * @create: 2020-07-09 23:45:33
 */
@Slf4j
public class TestThread {
  public static void main(String[] args) {
    //
    // new Thread(
    //         new Runnable() {
    //           @Override
    //           public void run() {
    //             log.info("执行任务");
    //           }
    //         })
    //     .start();

    // FutureTask task =
    //     new FutureTask<String>(
    //         new Callable<String>() { // 任务中传入回调接口
    //           @Override
    //           public String call() throws Exception {
    //             return "执行任务成功";
    //           }
    //         });
    // new Thread(task).start();
    // try {
    //   log.info("{}", task.get());
    // } catch (InterruptedException e) {
    //   e.printStackTrace();
    // } catch (ExecutionException e) {
    //   e.printStackTrace();
    // }

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    executorService.execute(
        new Runnable() {
          @Override
          public void run() {
            log.info("通过线程池执行任务");
          }
        });

    // 核心线程，最大线程，非核心线程存货时间，任务队列
    ExecutorService executorService1 =
        new ThreadPoolExecutor(
            100, 100, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));
  }
}
