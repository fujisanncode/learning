package ink.fujisann.learning.stock.job.ippool;

import ink.fujisann.learning.stock.dto.ProxyServer;
import ink.fujisann.learning.stock.job.ippool.observer.IpObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;

@Slf4j
public class PingProxyJob implements Runnable {
    private final ProxyServer proxyServer;

    private IpObserver ipObserver;

    public PingProxyJob(ProxyServer proxyServer, IpObserver ipObserver) {
        this.proxyServer = proxyServer;
        this.ipObserver = ipObserver;
    }

    @Override
    public void run() {
        String host = proxyServer.getIp() + ":" + proxyServer.getPort();
        //访问此网站，网站会返回访问端的ip，用于测试请求是否经过代理
        String targetUrl = "http://api.k780.com/?app=ip.local&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 代理
        requestFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer.getIp(), proxyServer.getPort())));
        //超时时间
        requestFactory.setConnectTimeout(300000);
        requestFactory.setReadTimeout(300000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        ResponseEntity<String> entity = null;
        try {
            entity = restTemplate.getForEntity(targetUrl, String.class);
        } catch (RestClientException e) {
            log.error("ping 失败 <<<<< {}", host);
        }
        if (!Objects.isNull(entity)) {
            log.debug("ping 成功 {} >>>>> {}", host, entity.getBody());

            // 找打ip后，通知ip观察者
            ipObserver.add(proxyServer);
        }
    }
}
