package ink.fujisann.learning.base.utils.mail;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件bean配置
 *
 * @author hulei
 * @date 2020-11-14 09:49:9:49
 * {@code @ConfigurationProperties} 支持松散绑定，支持批量绑定
 */
@ConfigurationProperties(prefix = "spring.mail")
@Configuration
@Setter
public class MailConfig {

    private String host;
    private String username;
    private String password;
    private String protocol;
    private String defaultEncoding;

    public String getUsername() {
        return username;
    }

    @Bean
    public JavaMailSender javaMailConfig() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setProtocol(protocol);
        javaMailSender.setDefaultEncoding(defaultEncoding);
        return javaMailSender;
    }
}
