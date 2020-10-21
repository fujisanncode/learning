package ink.fujisann.learning.base.listener.rabbitmq;

import ink.fujisann.learning.code.controller.upload.MultiFileManage;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// 消费者监听队列中的消息
@Component
@RabbitListener (queues = "direct queue")
@Slf4j
public class DirectListener {

    @Autowired
    private MultiFileManage multiFileManage;

    @RabbitHandler
    public void receiveMsg(Map msg) {
        // 接受rabbit mq服务器通道中的消息，并对消息进行消费
        log.info("consume msg ===> {}", msg.toString());
    }

    // 监听上传文件后的文件名称 然后调用服务取出文件，校验，解析数据入库
    @RabbitHandler
    public void receiveUploadFileName(String fileName) {
        log.info("receive file, fileName: {}.", fileName);
        int insertRows = 0;
        try {
            MultipartFile multipartFile = multiFileManage.getFile(fileName);
            insertRows = multiFileManage.parseExcelBatch(multipartFile);
        } catch (Exception e) {
            log.error("receiveUploadFileName error", e);
        }
        log.info("insert rows {} complete.", insertRows);
    }

}
