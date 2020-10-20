package com.example.sqllearning.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith (SpringRunner.class)
@SpringBootTest
public class TestFtp {

    @Autowired
    private FtpClientI ftpClientI;

    @Test
    public void testUploadFile() {
        try {
            log.info("test upload file");
            InputStream inputStream = new FileInputStream(new File("C:\\Users\\raiRezon\\Desktop\\new 1.txt"));
            ftpClientI.uploadFile(inputStream, "1.jpg", "/");
        } catch (FileNotFoundException e) {
            log.error("test upload file error {}", e);
        }
    }
}
