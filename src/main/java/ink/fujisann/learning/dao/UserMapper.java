package ink.fujisann.learning.dao;

import ink.fujisann.learning.vo.mybatis.Customer;
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