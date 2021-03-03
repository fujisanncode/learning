package ink.fujisann.learning.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProxyServer {
    private String ip;
    private Integer port;
}
