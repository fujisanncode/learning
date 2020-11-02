package ink.fujisann.learning.code.pojo.sys;

import ink.fujisann.learning.code.pojo.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 系统角色对应的权限点表，和角色多对多
 *
 * @author hulei
 * @date 2020/11/2
 */
@Entity
@Table(name = "sys_permission_t", uniqueConstraints = {@UniqueConstraint(name = "name_u", columnNames = "name")})
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Table(appliesTo = "sys_permission_t", comment = "系统接口权限点")
@Data
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseInfo {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @ApiModelProperty (hidden = true)
    private Integer id;

    @Column (unique = true, columnDefinition = "VARCHAR(50) DEFAULT 'admin' COMMENT '权限名称'")
    @ApiModelProperty (required = true)
    private String name;

}
