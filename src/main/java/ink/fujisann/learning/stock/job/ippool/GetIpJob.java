package ink.fujisann.learning.stock.job.ippool;

import cn.hutool.core.thread.ThreadUtil;
import ink.fujisann.learning.stock.dto.ProxyServer;
import ink.fujisann.learning.stock.job.ippool.observer.IpObserverImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * 开启多线程查询快代理页面
 *
 * @author raiRezon
 * @version 2021/2/3
 */
@Slf4j
public class GetIpJob implements Callable<List<ProxyServer>> {

    /**
     * 当前查询的页码
     */
    private final Integer pageNum;

    /**
     * 本次查询使用的代理
     */
    private final ProxyServer proxyServer;

    public GetIpJob(Integer pageNum, ProxyServer proxyServer) {
        this.pageNum = pageNum;
        this.proxyServer = proxyServer;
    }

    @Override
    public List<ProxyServer> call() {
        // 每个线程中查询20个快代理页面，大约耗时20~40s
        List<ProxyServer> proxyServerList = new ArrayList<>();
        // 快代理普通代理
        //String kdl = "https://www.kuaidaili.com/free/inha/" + i + "/";
        //快代理高匿名代理
        //String kdl = "https://www.kuaidaili.com/free/intr/" + pageNum + "/";
        // 西拉代理
        String kdl = "http://www.xiladaili.com/gaoni/" + pageNum + "/";

        getIpFromPage(kdl, proxyServer);

        // 此ip重新入池
        new IpObserverImpl().add(proxyServer);

        return proxyServerList;
    }

    //调用西拉代理接口
    private void getIpByXldl() {

    }

    /**
     * 调用一次接口查询ip
     *
     * @param pageUrl     查询代理的url
     * @param proxyServer 本次使用的代理
     */
    @SneakyThrows
    private void getIpFromPage(String pageUrl, ProxyServer proxyServer) {
        // 休眠10s，避免ip被封
        Thread.sleep(10000);

        // 访问https
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        // 设置代理
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf)
                .setProxy(new HttpHost(proxyServer.getIp(), proxyServer.getPort()))
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        // 设置超时时间
        requestFactory.setConnectTimeout(300000);
        requestFactory.setReadTimeout(300000);
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        ResponseEntity<String> entity;

        // 异常则跳过
        try {
            entity = restTemplate.getForEntity(pageUrl, String.class);
        } catch (RestClientException e) {
            log.debug("调用快代理异常 {}", e.getMessage());
            return;
        }

        if (Objects.isNull(entity.getBody())) {
            log.error("get ip entity {}", entity);
            return;
        }

        if (pageUrl.contains("kuaidaili")) {
            parseKdl(entity);
        } else if (pageUrl.contains("xiladaili")) {
            parseXldl(entity);
        }
    }

    /**
     * 调用西拉代理页面查询
     *
     * @param entity 页面响应
     */
    private void parseXldl(ResponseEntity<String> entity) {
        Document document = Jsoup.parse(Objects.requireNonNull(entity.getBody()));
        Element tbody = document.select("table[class=fl-table]").select("tbody").get(0);
        Elements trList = tbody.select("tr");
        for (Element tr : trList) {
            Elements tdList = tr.select("td");
            String ip = tdList.get(0).text();
            String type = tdList.get(1).text();
            String[] ipArr = ip.split(":");
            ProxyServer proxyServer = new ProxyServer(ipArr[0], Integer.parseInt(ipArr[1]));

            // 测试页面上得每一个ip
            ThreadUtil.execute(new PingProxyJob(proxyServer, new IpObserverImpl()));
        }
    }

    /**
     * 解析快代理
     *
     * @param entity 接口响应
     */
    private void parseKdl(ResponseEntity<String> entity) {
        Document document = Jsoup.parse(Objects.requireNonNull(entity.getBody()));
        Element list = document.getElementById("list");
        if (Objects.isNull(list)) {
            return;
        }
        Element tbody = list.select("table").select("tbody").get(0);
        Elements trList = tbody.select("tr");
        for (Element tr : trList) {
            Elements tdList = tr.select("td");
            String ip = tdList.get(0).text();
            int port = Integer.parseInt(tdList.get(1).text());

            // 测试页面上得每一个ip
            ThreadUtil.execute(new PingProxyJob(new ProxyServer(ip, port), new IpObserverImpl()));
        }
    }
}
