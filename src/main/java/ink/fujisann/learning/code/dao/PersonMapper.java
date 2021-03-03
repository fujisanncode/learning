package ink.fujisann.learning.code.dao;

import ink.fujisann.learning.code.mybatis.Person;

//@Component
public interface PersonMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}