package com.example.sqllearning.utils.ftp;

import com.example.sqllearning.exception.BusinessException;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

//component service bean等都是向容器注入实例   注入容器的实例 可以通过autowire进行装配
@Slf4j
@Service
public class FtpClientImpl implements FtpClientI {
    //@Autowired
    //private FTPClient ftpClient;

    @Override
    public Boolean uploadFile(InputStream inputStream, String remoteFileName, String remoteDir) {
        FTPClient ftpClient = FtpUtil.ftpClient();
        try {
            ftpClient.changeWorkingDirectory(remoteDir);
            // ftp服务器被动模式 服务器暴露端口被客户端连接  (主动连接是服务器主动连接到客户端的端口返回数据)
            //ftpClient.enterLocalPassiveMode();
            // 解决图片上传 图片内容错误的情况
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            Boolean success = ftpClient.storeFile(remoteFileName, inputStream);
            if (!success) {
                //抛出自定义的运行异常给调用接口处
                // throw new BusinessException("upload file fail");
            }
            ftpClient.logout();
            return true;
        } catch (IOException e) {
            log.error("upload file fail io error {}", e);
            // throw new BusinessException("upload file fail io error");
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("finally, upload file fail io error {}", e);
                    throw new BusinessException();
                }
            }
            return false;
        }
    }

    @Override
    public void downloadFile(String remoteFileName, String localFileName, String remoteDir) {

    }

    @Override
    public String readFileToBase64(String remoteFileName, String remoteDir) {
        return null;
    }

    @Override
    public InputStream readFileToIn(String remoteFileName, String remoteDir) {
        FTPClient ftpClient = FtpUtil.ftpClient();
        InputStream in = null;
        try {
            ftpClient.changeWorkingDirectory(remoteDir);
            FTPFile[] ftpFiles = ftpClient.listFiles(remoteDir);
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.getName().equals(remoteFileName)) {
                    in = ftpClient.retrieveFileStream(remoteDir + remoteFileName);
                    break;
                }
            }
            // ftpClient.logout();
            return in;
        } catch (IOException e) {
            log.error("read file from ftp {}", e);
            return null;
        } finally {
            // 关闭ftp连接 关闭文件流
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("disconnect ftp client error {}", e);
                }
            }
            //if (in != null) {
            //    try {
            //        in.close();
            //    } catch (IOException e) {
            //        log.error("close in {}", e);
            //    }
            //}
        }
    }
}
