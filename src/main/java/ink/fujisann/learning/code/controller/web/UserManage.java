package ink.fujisann.learning.code.controller.web;

import ink.fujisann.learning.base.utils.MessageUtil;
import ink.fujisann.learning.code.dao.UserMapper;
import ink.fujisann.learning.code.mybatis.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
        return customer;
    }
}
