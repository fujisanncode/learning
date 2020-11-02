package ink.fujisann.learning.code.pojo.sys;

import ink.fujisann.learning.code.pojo.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 系统登录用户，和角色多对多
 *
 * @author hulei
 * @date 2020/11/2
 */
@Entity
@Table(name = "sys_user_t", uniqueConstraints = {@UniqueConstraint(name = "name_u", columnNames = {"name"})})
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Table(appliesTo = "sys_user_t", comment = "系统用户表")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseInfo {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @ApiModelProperty (hidden = true)
    private Integer id;

    @Column(unique = true, columnDefinition = "varchar(50) not null comment '登录用户名称'")
    @ApiModelProperty(required = true)
    private String name;

    @Column (columnDefinition = "VARCHAR(50) DEFAULT '123' COMMENT '登录用户密码'")
    @ApiModelProperty (required = true)
    private String password;

    @Column (columnDefinition = "VARCHAR(100) DEFAULT '测试默认值' COMMENT '备注'")
    @ApiModelProperty (hidden = true)
    private String remark;

    // 设置级联关系类型，是否立即加载，指定关联字段
    // 级联关系：Login是关系维护端，操作Login，会级联Role进行修改
    // @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "login")
    // private List<Role> roles;

}