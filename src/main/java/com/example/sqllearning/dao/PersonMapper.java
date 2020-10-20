package com.example.sqllearning.dao;

import com.example.sqllearning.vo.mybatis.Person;
import org.springframework.stereotype.Component;

@Component
public interface PersonMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}