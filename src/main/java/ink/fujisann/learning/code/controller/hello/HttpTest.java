package ink.fujisann.learning.code.controller.hello;

import ink.fujisann.learning.base.utils.common.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api ("测试http调用")
public class HttpTest {

    @PostMapping ("/testPost")
    @ApiOperation ("测试post接口调用")
    public String testPost(String uName) {
        HttpClient httpClient = new HttpClient();
        String url = "http://api.nnzhp.cn/api/user/add_stu";
        PostMethod postMethod = new PostMethod(url);
        // 设置响应字符集
        postMethod.getParams().setParameter(
            HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "奥利奇");
        jsonObject.put("grade", "八年级");
        String now = DateUtil.format(new Date(), "ddHHmmssSSS");
        jsonObject.put("phone", now);
        try {
            // 设置请求头字符集
            postMethod.addRequestHeader(
                "Content-Type", "application/json;charset=UTF-8");
            RequestEntity requestEntity = new StringRequestEntity(
                jsonObject.toJSONString(), "application/json", "UTF-8");
            // 设置请求体
            postMethod.setRequestEntity(requestEntity);
            long start = new Date().getTime();
            log.info("testPost url {}, param {}", url, jsonObject.toJSONString());
            httpClient.executeMethod(postMethod);
            log.info("testPost cost ===> {} ms", new Date().getTime() - start);
            // 获取响应结果
            String responseStr = postMethod.getResponseBodyAsString();
            log.info("testPost rt ===> {}", responseStr);
            // 释放连接
            postMethod.releaseConnection();
            return responseStr;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "testPost";
    }
}
