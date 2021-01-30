package ink.fujisann.learning.stock.controller;

import cn.hutool.core.date.DateUtil;
import ink.fujisann.learning.stock.util.JsEngine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
public class DelayTask implements Runnable, Delayed {
    /**
     * 任务名称
     */
    private String taskName;

    /**
     * xxx ms后执行任务
     */
    private long execTime;

    public DelayTask(String taskName, long execTime) {
        this.taskName = taskName;
        // 执行任务的时间点
        this.execTime = System.currentTimeMillis() + execTime;
    }

    /**
     * 决定延迟任务执行顺序的方法
     *
     * @param o 和当前任务对比的任务
     * @return execTime值越大的任务越晚被执行
     */
    @Override
    public int compareTo(@NotNull Delayed o) {
        return (int) (this.execTime - ((DelayTask) o).execTime);
    }

    /**
     * 延时任务的内容
     */
    @Override
    public void run() {
        log.warn("{} 执行延迟任务", this.taskName);

        // 延时任务中需要调用js方法，重新获取token
        DataController.jsToken = JsEngine.getThsCookie();

        // 重新生成延时任务，放到队列中
        DataController.delayQueue.put(new DelayTask(DateUtil.now(), 120000));
    }

    /**
     * 用于动态计算任务到执行的剩余时间
     *
     * @param unit delay实现类提供的时间对象
     * @return 必须通过unit进行时间装换
     */
    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        long remainTime = this.execTime - System.currentTimeMillis();
        // 源时间差，源时间差的单位
        return unit.convert(remainTime, TimeUnit.MILLISECONDS);
    }
}
