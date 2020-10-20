package com.example.sqllearning.controller.person;

import com.example.sqllearning.dao.PersonMapper;
import com.example.sqllearning.vo.mybatis.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/person-manage")
@Api (value = "person manage", tags = "人员管理")
@Slf4j
public class PersonManage {

    @Autowired
    private PersonMapper personMapper;

    @GetMapping ("/query-one-person/{personId}")
    @ApiOperation (value = "query one person", notes = "根据主键id查询人员信息")
    public Person queryOnePerson(@PathVariable ("personId") int personId) {
        return personMapper.selectByPrimaryKey(personId);
    }

    @PostMapping ("/insert-one-person")
    @ApiOperation (value = "insert-one-person", notes = "插入人员信息")
    // @requestBody指定为json格式的入参
    public int insertPerson(@RequestBody Person person) {
        return personMapper.insert(person);
    }

    // 数组中数据解析为User对象
    public Person parsePersonFromList(ArrayList<String> firstRow) {
        Person person = new Person();
        person.setFirstName(firstRow.get(1));
        person.setLastName(firstRow.get(2));
        person.setAddressLv1(firstRow.get(3));
        person.setAddressLv1Code(firstRow.get(4));
        person.setAddressLv2(firstRow.get(5));
        person.setAddressLv2Code(firstRow.get(6));
        person.setAddressLv3(firstRow.get(7));
        person.setAddressLv3Code(firstRow.get(8));
        person.setAddressLv4(firstRow.get(9));
        return person;
    }
}
