package ink.fujisann.learning.base.utils.common;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取文本的工具
 *
 * @author hulei
 * @version 2020/10/26
 */
@Slf4j
@SuppressWarnings("unused")
public class ReadUtil {

    /**
     * 从json文件中读取指定字段
     *
     * @param key 关键字
     * @return key对应的value
     */
    public static String readJson(String key) {
        String file = "mock/response.json";
        JSONObject object = JSONObject.parseObject(read(file));
        return object.getString(key);
    }
    
    /**
     * 将properties文件中的unicode注释转为汉字
     */
    public static void unicodeTransfer() {
        String file = "application-dev.yml";
        write(read(file), file);
    }

    private static String read(String file) {
        StringBuilder result = new StringBuilder();
        ClassLoader classLoader = ReadUtil.class.getClassLoader();
        try (InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(file));
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String s;
            while ((s = reader.readLine()) != null) {
                result.append(unicodeStr2String(s)).append("\r\n");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return result.toString();
    }

    @SneakyThrows
    private static void write(String content, String fileName) {
        ClassLoader classLoader = ReadUtil.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(content);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private static String unicode2String(String unicode) {
        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    private static String unicodeStr2String(String unicodeStr) {
        int length = unicodeStr.length();
        int count = 0;
        //正则匹配条件，可匹配“\\u”1到4位，一般是4位可直接使用 String regex = "\\\\u[a-f0-9A-F]{4}";
        String regex = "\\\\u[a-f0-9A-F]{1,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unicodeStr);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            //原本的Unicode字符
            String oldChar = matcher.group();
            //转换为普通字符
            String newChar = unicode2String(oldChar);
            // 在遇见重复出现的unicode代码的时候会造成从源字符串获取非unicode编码字符的时候截取索引越界等
            int index = matcher.start();
            //添加前面不是unicode的字符
            sb.append(unicodeStr, count, index);
            //添加转换后的字符
            sb.append(newChar);
            //统计下标移动的位置
            count = index + oldChar.length();
        }
        //添加末尾不是Unicode的字符
        sb.append(unicodeStr, count, length);
        return sb.toString();
    }
}
