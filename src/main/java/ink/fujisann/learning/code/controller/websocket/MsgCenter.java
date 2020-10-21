package ink.fujisann.learning.code.controller.websocket;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping ("/msg-center")
@Api (value = "msg center", tags = "webSocket服务")
public class MsgCenter {

    // 页面请求
    @GetMapping ("/socket/{sessionId}")
    @ApiOperation (value = "socket", notes = "页面请求")
    public ModelAndView socket(@PathVariable ("sessionId") String sessionId) {
        ModelAndView modelAndView = new ModelAndView("/socket");
        modelAndView.addObject("sessionId", sessionId);
        // 跳转到页面
        return modelAndView;
    }

    // 推送消息给页面
    @GetMapping ("/push-to-web/{sessionId}")
    @ApiOperation (value = "push to web", notes = "推送消息给页面")
    public String pushToWeb(@PathVariable String sessionId) {
        WebSocketServer.sendInfo("api /push-to-web", sessionId);
        return "ok";
    }
}
