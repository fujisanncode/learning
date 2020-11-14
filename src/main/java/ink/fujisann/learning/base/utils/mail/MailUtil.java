package ink.fujisann.learning.base.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 发送邮件工具类
 *
 * @author hulei
 * @date 2020-11-14 09:38:9:38
 */
@Service
public class MailUtil {
    private static JavaMailSender MAIL_SENDER = null;

    private static String FROM = null;

    @Autowired
    public void setJavaMailSender(@Qualifier("javaMailConfig") JavaMailSender javaMailSender) {
        MAIL_SENDER = javaMailSender;
    }

    @Autowired
    public void setFrom(MailConfig mainConfig) {
        FROM = mainConfig.getUsername();
    }

    public static void sendSimpleMailDefault(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(FROM);
        message.setSubject(subject);
        message.setText(text);
        MAIL_SENDER.send(message);
    }

    public static void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        MAIL_SENDER.send(message);
    }
}
