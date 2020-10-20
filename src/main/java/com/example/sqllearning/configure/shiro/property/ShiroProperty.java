package com.example.sqllearning.configure.shiro.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

// 从配置文件中读取常量
@Data
@Component
@PropertySource (value = "classpath:bean-property/shiro.properties")
@ConfigurationProperties (prefix = "shiro.property")
public class ShiroProperty {

    private String loginUrl;
    private String noLoginUrl;
    private String logoutUrl;
}
