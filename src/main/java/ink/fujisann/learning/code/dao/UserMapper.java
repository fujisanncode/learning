package ink.fujisann.learning.code.dao;

import ink.fujisann.learning.code.pojo.mybatis.Customer;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
}