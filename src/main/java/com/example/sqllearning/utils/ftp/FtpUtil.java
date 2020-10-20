package com.example.sqllearning.utils.ftp;

import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class FtpUtil {

    private static String ftpHost;

    private static Integer ftpPort;

    private static String filePath;

    private static String userName;

    private static String passWord;

    //对象实例注入容器 用在component 或者 configuration的类下   注入容器导致ftpclient服务再次连接
    //@Bean
    public static FTPClient ftpClient() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(3000);
        ftpClient.setControlEncoding("utf-8");
        try {
            ftpClient.connect(ftpHost, ftpPort);
            ftpClient.login(userName, passWord);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                log.info("connect to ftp server fail");
                return null;
            } else {
                log.info("connect to ftp server success");
                return ftpClient;
            }
        } catch (IOException e) {
            log.error("ip or port error {}", e);
            return null;
        }
    }

    @Value ("${ftp.host}")
    public void setFtpHost(String ftpHost) {
        FtpUtil.ftpHost = ftpHost;
    }

    @Value ("${ftp.port}")
    public void setFtpPort(Integer ftpPort) {
        FtpUtil.ftpPort = ftpPort;
    }

    @Value ("${ftp.filepath}")
    public void setFilePath(String filePath) {
        FtpUtil.filePath = filePath;
    }

    @Value ("${ftp.username}")
    public void setUserName(String userName) {
        FtpUtil.userName = userName;
    }

    @Value ("${ftp.password}")
    public void setPassWord(String passWord) {
        FtpUtil.passWord = passWord;
    }
}
