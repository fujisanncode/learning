package ink.fujisann.learning.stock.job.ippool;

import ink.fujisann.learning.stock.dto.ProxyServer;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class IpPool {

    public static final Deque<ProxyServer> IP_POOL = new ConcurrentLinkedDeque<>();

    public static final AtomicInteger PAGE_NUM = new AtomicInteger(1);

    public static final int MAX_PAGE = 200;
}
