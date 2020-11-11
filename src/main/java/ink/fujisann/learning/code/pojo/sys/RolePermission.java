package ink.fujisann.learning.code.pojo.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * 系统角色-接口权限点关联表
 *
 * @author hulei
 * @date 2020/11/2
 */
@Entity
@Table(name = "sys_role_permission_t")
@org.hibernate.annotations.Table(appliesTo = "sys_role_permission_t", comment = "系统角色-接口权限点关联表")
@Data
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 不设置级联操作
     */
    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    /**
     * 不设置级联操作
     */
    @ManyToOne
    @JoinColumn(name = "permissionId", nullable = false)
    private Permission permission;
}
