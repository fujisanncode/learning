package ink.fujisann.learning.stock.job.ippool.observer;

import cn.hutool.core.thread.ThreadUtil;
import ink.fujisann.learning.stock.dto.ProxyServer;
import ink.fujisann.learning.stock.job.ippool.GetIpJob;
import ink.fujisann.learning.stock.job.ippool.IpPool;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IpObserverImpl implements IpObserver {
    @Override
    public void add(ProxyServer proxyServer) {
        if (IpPool.PAGE_NUM.get() > IpPool.MAX_PAGE) {
            // 如果任务执行完毕，则ip归还ip池
            IpPool.IP_POOL.addLast(proxyServer);
            log.info("ip池大小 {}", IpPool.IP_POOL.size());
        } else {
            // 如果任务没有执行完毕，则开一个线程继续执行
            ThreadUtil.execAsync(new GetIpJob(IpPool.PAGE_NUM.getAndIncrement(), proxyServer));
        }
    }
}
