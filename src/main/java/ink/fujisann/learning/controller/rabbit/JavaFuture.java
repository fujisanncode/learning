package ink.fujisann.learning.controller.rabbit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/async")
@Api (value = "async", tags = "异步方法服务")
@Slf4j
public class JavaFuture {

    @Autowired
    private FutureAsync futureAsync;

    @GetMapping ("/java-future")
    @ApiOperation (value = "async future", notes = "通过future实现异步")
    public String asyncFuture() {
        long start = new Date().getTime();
        log.info("asyncFuture starting");
        try {
            String r1 = futureAsync.jobOne();
            Future<String> r2 = futureAsync.jobTwo();
            Future<String> r3 = futureAsync.jobThree();
            while (true) {
                // 通过回调统计异步执行情况
                if (r2.isDone() && r3.isDone()) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            log.error("async future {}", e);
        }
        log.info("asyncFuture cost {}ms", new Date().getTime() - start);
        return "ok";
    }
}
