package com.example.sqllearning.controller.rabbit;

import com.example.sqllearning.configure.rabbitmq.DirectExchangeConfig;
import com.example.sqllearning.configure.rabbitmq.FanoutExchangeConfig;
import com.example.sqllearning.configure.rabbitmq.TopicExchangeConfig;
import com.example.sqllearning.controller.upload.MultiFileManage;
import com.example.sqllearning.utils.common.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping ("/rabbitmq")
@Api (value = "rabbitmq", tags = "004-MQ提供者服务")
public class RabbitProviderController {

    // 通过模板调用rabbit的发送消息/接受消息的方法
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MultiFileManage multiFileManage;

    @GetMapping ("/send-msg")
    @ApiOperation (value = "send-msg", notes = "携带路由键值的消息消息发送到直连交换机")
    public String sendMsg() {
        Map<String, String> msgMap = commonMsg();
        msgMap.put("msgContent", "hello rabbitmq");
        // 将携带绑定键值的消息，发送到交换机
        rabbitTemplate.convertAndSend("direct exchange", "directRouting", msgMap);
        return "ok";
    }

    @GetMapping ("/man-topic-msg")
    @ApiOperation (value = "man-topic-msg", notes = "携带topic.man路由键值的消息发送到主题交换机")
    public String manTopicMsg() {
        Map<String, String> msgMap = commonMsg();
        msgMap.put("msgContent", "man msg");
        // 将携带绑定键值的消息，发送到交换机
        rabbitTemplate.convertAndSend(TopicExchangeConfig.topicExchange, TopicExchangeConfig.manKey, msgMap);
        return "ok";
    }

    @GetMapping ("/woman-topic-msg")
    @ApiOperation (value = "woman-topic-msg", notes = "携带topic.woman路由键值的消息发送到主题交换机（特定的绑定规则）")
    public String womanTopicMsg() {
        Map<String, String> msgMap = commonMsg();
        msgMap.put("msgContent", "woman msg");
        // 将携带绑定键值的消息，发送到交换机
        rabbitTemplate.convertAndSend(TopicExchangeConfig.topicExchange, TopicExchangeConfig.womanKey, msgMap);
        return "ok";
    }

    @GetMapping ("/fan-out-msg")
    @ApiOperation (value = "fan-out-msg", notes = "不携带路由键值的消费发送到扇形交换机")
    public String fanoutMsg() {
        Map<String, String> msgMap = commonMsg();
        msgMap.put("msgContent", "fanout msg");
        rabbitTemplate.convertAndSend(FanoutExchangeConfig.fanoutExchange, null, msgMap);
        return "ok";
    }

    public Map<String, String> commonMsg() {
        String msgId = String.valueOf(UUID.randomUUID());
        String msgContent = "common msg";
        String msgDate = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("msgId", msgId);
        msgMap.put("msgContent", msgContent);
        msgMap.put("msgDate", msgDate);
        return msgMap;
    }

    @PostMapping ("/upload-async")
    @ApiOperation (value = "upload async", notes = "异步上传")
    public String uploadAsync(MultipartFile file) {
        String fileName = multiFileManage.uploadFile(file);
        rabbitTemplate.convertAndSend(DirectExchangeConfig.directExchangeName, DirectExchangeConfig.directRouteKey, fileName);
        return "ok";
    }

}
