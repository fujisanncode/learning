package ink.fujisann.learning.stock.controller;

import cn.hutool.core.date.DateUtil;
import ink.fujisann.learning.stock.constant.TongHuaShun;
import ink.fujisann.learning.stock.util.JsEngine;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
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
        //getFreeIp();

        //测试代理
        testProxy();

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

    private static void getFreeIp() throws IOException {
        String kdl = "https://www.kuaidaili.com/free/inha/3/";
        Document document = Jsoup.connect(kdl).get();
        Element list = document.getElementById("list");
        Element tbody = list.select("table").select("tbody").get(0);
        Elements trList = tbody.select("tr");
        for (Element tr : trList) {
            Elements tdList = tr.select("td");
            String ip = tdList.get(0).text();
            int port = Integer.parseInt(tdList.get(1).text());
            log.debug("{}:{}", ip, port);
        }
    }

    private static void testProxy() throws IOException {
        //访问此网站，网站会返回访问端的ip，用于测试请求是否经过代理
        String targetUrl = "http://myip.ipip.net";

        // 代理服务器IP
        String ip = "146.56.227.20";
        int port = 8081;

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));

        OkHttpClient client = new OkHttpClient.Builder()
                .proxy(proxy)
                .build();

        Request request = new Request.Builder()
                .url(targetUrl)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
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
