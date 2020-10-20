package ink.fujisann.learning.repository;

import ink.fujisann.learning.vo.sys.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @description: 用户表
 *               访问数据库的接口层，启动类中定义dao为接口扫描层，jpa接口不能定义在dao包中
 * @author: hulei
 * @create: 2020-03-18 20:51:03
 */
@Component
public interface UserRepository extends CrudRepository<User, Integer> {

    // 根据用户名查询用户信息，登录接口比对登录用户信息使用
    public User findUserByName(String name);

}