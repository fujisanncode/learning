package ink.fujisann.learning.stock.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import ink.fujisann.learning.stock.constant.TongHuaShun;
import ink.fujisann.learning.stock.dto.ProxyServer;
import ink.fujisann.learning.stock.job.ippool.GetIpJob;
import ink.fujisann.learning.stock.job.ippool.IpPool;
import ink.fujisann.learning.stock.util.JsEngine;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.DelayQueue;

@Slf4j
@Api(tags = {"证券数据落地"})
@RequestMapping("/data")
@RestController
public class DataController {

    // 定义延时任务队列
    public static DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

    public static String jsToken = "";

    @SneakyThrows
    public static void main(String[] args) {
        //从快代理网站获取免费代理
        getFreeIp();

        //log.debug(DateUtil.now());
        //
        //// 启动一个线程用于消费延时队列
        //ThreadUtil.execute(new ConsumerDelayTask());
        //
        //// 循环调用ths接口
        //for (int i = 0; i < 15; i++) {
        //    getData();
        //}
    }

    @SneakyThrows
    private static void getFreeIp() {
        ProxyServer httpDefaultProxy = new ProxyServer("146.56.227.20", 8081);
        //ProxyServer httpsDefaultProxy = new ProxyServer("146.56.227.20", 8082);
        ThreadUtil.execAsync(new GetIpJob(IpPool.PAGE_NUM.getAndIncrement(), httpDefaultProxy));
    }

    private static void getData() {
        RestTemplate template = new RestTemplate();

        HttpHeaders requestHeader = ThsHeader();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeader);

        ResponseEntity<String> entity = template.exchange(TongHuaShun.REAL_HEAD, HttpMethod.GET, requestEntity, String.class);
        String body = entity.getBody();
        log.error(">>> {}" + body);
    }

    private static HttpHeaders ThsHeader() {
        HttpHeaders requestHeader = new HttpHeaders();
        // 伪装浏览器请求
        String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36";
        requestHeader.add("User-Agent", agent);

        // 第一次调用js获取token
        if (StringUtils.isEmpty(jsToken)) {
            DataController.jsToken = JsEngine.getThsCookie();
            DataController.delayQueue.put(new DelayTask(DateUtil.now(), 120000));
        }

        String cookie = "v=" + DataController.jsToken;
        requestHeader.add("Cookie", cookie);
        return requestHeader;
    }
}
