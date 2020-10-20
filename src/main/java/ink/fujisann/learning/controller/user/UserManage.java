package ink.fujisann.learning.controller.user;

import ink.fujisann.learning.dao.UserMapper;
import ink.fujisann.learning.utils.MessageUtil;
import ink.fujisann.learning.utils.common.DateUtil;
import ink.fujisann.learning.vo.mybatis.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping ("/user-api")
@Api (value = "user-manage", tags = "登录用户管理")
public class UserManage {

    @Autowired
    private UserMapper userMapper;

    @GetMapping ("/query-user-by-id/{id}")
    @ApiOperation (value = "查询用户", notes = "传入用户表主键")
    public Customer queryUserByCondition(@PathVariable (value = "id") Integer userId) {
        // TeacherTMapper teacherTMapper = SpringContextHolder.getApplicationContext().getBean(TeacherTMapper.class);
        // List<ArticleT> articleTS = teacherTMapper.selectAll();
        log.info(MessageUtil.getMessage("user.name"));
        Customer queryUsersRt = new Customer();
        queryUsersRt = userMapper.selectByPrimaryKey(userId);
        return queryUsersRt;
    }

    @PostMapping ("/insert-one-user")
    @ApiOperation (value = "insert-one-user", notes = "插入一个用户")
    public int insertUser(Customer customer) {
        int insertRows = userMapper.insert(customer);
        return 1;
    }

    // 数组中数据解析为User对象
    public Customer parseUserFromList(ArrayList<String> firstRow) {
        Customer customer = new Customer();
        customer.setId(Integer.valueOf(firstRow.get(0)));
        customer.setUserNum(firstRow.get(1));
        customer.setUserName(firstRow.get(2));
        customer.setUserPassword(firstRow.get(3));
        customer.setSex(firstRow.get(5));
        customer.setMobile(firstRow.get(7));
        customer.setUpdateDate(DateUtil.parse(firstRow.get(9), DateUtil.dateFormat1));
        customer.setLan(firstRow.get(11));
        return customer;
    }
}
