package ink.fujisann.learning.base.utils;

import ink.fujisann.learning.code.dao.UserMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    private static MessageSource messageSource;
    private static UserMapper userMapper;

    // autowire不能注入静态变量 通过构造初始化静态变量
    public MessageUtil(MessageSource messageSource, UserMapper userMapper) {
        // messageSource本身就已经注入spring中 userMapper并没有注入
        MessageUtil.messageSource = messageSource;
        MessageUtil.userMapper = userMapper;
    }

    // 获取国际化翻译的值
    public static String getMessage(String key) {
        // 请求头中zh-CN转为zh_CN 读取国际化文件
        String rtMsg = messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        // userMapper.selectByPrimaryKey(1);
        return rtMsg;
    }
}
