package ink.fujisann.learning.code.controller.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员接口
 *
 * @author hulei
 * @version 2020/11/2
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员接口")
public class AdminController {

    @ApiOperation("查找页面的菜单")
    @GetMapping("/findMenu")
    public String findMenu() {
        return "{\"data\":[{\"path\":\"/home\",\"name\":\"home\",\"component\":\"components/home/home\",\"meta\":{\"hidden\":\"false\",\"icon\":\"account_balance\",\"icon-alt\":\"account_balance\",\"text\":\"主页\",\"model\":\"false\"}}]}";
    }
}
