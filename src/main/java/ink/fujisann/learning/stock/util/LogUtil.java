package ink.fujisann.learning.stock.util;

import ink.fujisann.learning.stock.controller.DataController;
import lombok.SneakyThrows;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.InputStream;

/**
 * 加载日志配置的工具类
 *
 * @author raiRezon
 * @version 2021/1/30
 */
public class LogUtil {
    /**
     * 加载自定义的log4j2.xml配置
     */
    @SneakyThrows
    public static void load() {
        InputStream inputStream = DataController.class.getResource("/log4j2.xml").openStream();
        ConfigurationSource logSource = new ConfigurationSource(inputStream);
        Configurator.initialize(null, logSource);
    }
}
