package ink.fujisann.learning.code.pojo.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * 系统用户-角色关联表
 *
 * @author hulei
 * @date 2020/11/2
 */
@Entity
@Table(name = "sys_user_role_t")
@org.hibernate.annotations.Table(appliesTo = "sys_user_role_t", comment = "系统用户-角色关联表")
@Data
public class UserRole {

    /**
     * 此注解指定主键（指定自增主键生成策略，对于mysql是自增策略）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * {@code @ManyToOne} 指定增删改查的级联关系<br/>
     * {@code @JoinColumn} 指定关联表中列名<br/>
     */
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;
}
