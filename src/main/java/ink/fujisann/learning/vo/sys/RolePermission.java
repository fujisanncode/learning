package ink.fujisann.learning.vo.sys;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * 系统角色-接口权限点关联表
 */
@Entity// 此注解会在数据库生成表（字段为驼峰，数据库为下划线）
@Table (name = "sys_role_permission_t")
@org.hibernate.annotations.Table (appliesTo = "sys_role_permission_t", comment = "系统角色-接口权限点关联表")// appliesTo不能使用大写表名
@Data
public class RolePermission {

    @Id// 此注解指定主键（指定自增主键生成策略，对于mysql是自增策略）
    @GeneratedValue (strategy = GenerationType.AUTO)
    @ApiModelProperty (hidden = true)
    private Integer id;

    @ManyToOne //关联表多条记录对user表一条记录
    @JoinColumn (name = "role_id", nullable = false) //表中列名
    private Role role;

    @ManyToOne
    @JoinColumn (name = "permission_id", nullable = false)// 表中列名
    private Permission permission;
}
