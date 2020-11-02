package ink.fujisann.learning.code.service.impl.runner;

import ink.fujisann.learning.base.utils.common.LambdaUtil;
import ink.fujisann.learning.base.utils.common.ScanAnnotation;
import ink.fujisann.learning.code.controller.web.ShiroController;
import ink.fujisann.learning.code.pojo.sys.Permission;
import ink.fujisann.learning.code.pojo.sys.Role;
import ink.fujisann.learning.code.pojo.sys.User;
import ink.fujisann.learning.code.pojo.sys.UserRole;
import ink.fujisann.learning.code.repository.UserRepository;
import ink.fujisann.learning.code.service.ShiroService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * spring启动后<br/>
 * shiro的权限点写入数据库<br/>
 * 创建默认的用户，并绑定用户角色、角色权限<br/>
 *
 * @author hulei
 * @version 2020/11/2
 */
@Component
public class ShiroRunner implements ApplicationRunner {
    
    private ShiroService shiroService;

    @Autowired
    public void setShiroService(ShiroService shiroService) {
        this.shiroService = shiroService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 用户角色级联保存
        shiroService.addDefaultUser();

        // 扫描权限点并保存
        shiroService.addExistPermission();
    }
}
