package ink.fujisann.learning.code.dao;

import ink.fujisann.learning.code.pojo.mybatis.Customer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户表
 *
 * @author raiRezon
 * @date 2020/11/16
 */
@Repository
public interface CustomerDao {

    /**
     * 查询所有客户
     *
     * @return
     */
    List<Customer> findCustomerAll();
}