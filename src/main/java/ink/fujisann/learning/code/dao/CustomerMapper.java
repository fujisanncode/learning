package ink.fujisann.learning.code.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.fujisann.learning.code.mybatis.Customer;
import ink.fujisann.learning.code.resp.CustomerResp;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMapper extends BaseMapper<Customer> {
    /**
     * 查询客户列表
     * @return 客户列表
     */
    List<CustomerResp> listCustomer();
}