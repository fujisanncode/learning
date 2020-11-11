package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.sys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * 用户表：访问数据库的接口层，启动类中定义dao为接口扫描层，jpa接口不能定义在dao包中
 *
 * @author hulei
 * @date 2020-03-18 20:51:03
 */
@Component
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 根据用户名查询用户信息，登录接口比对登录用户信息使用
     *
     * @param name 姓名
     * @return 用户
     */

    User findUserByName(String name);

    /**
     * 是否存在指定姓名的user
     *
     * @param name 姓名
     * @return true 存在
     */
    Boolean existsUserByName(String name);

}