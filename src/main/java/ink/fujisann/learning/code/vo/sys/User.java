package ink.fujisann.learning.code.vo.sys;

import ink.fujisann.learning.code.vo.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 系统登录用户，和角色多对多
 */
@Entity //此注解会在数据库生成表（字段为驼峰，数据库为下划线）
@Table (name = "sys_user_t")
@DynamicInsert //insert的sql中，忽略null字段，否则会将对象中的null写入数据库；入库数据库字段需要空，设置为空字符串
@DynamicUpdate
@org.hibernate.annotations.Table (appliesTo = "sys_user_t", comment = "系统用户表") //表注释，appliesTo不能使用大写表名
@Data
@EqualsAndHashCode (callSuper = true) //@data生成的equals方法，不含父类的字段；如果需要父类的字段比较，必须此属性
public class User extends BaseInfo {

    // 此注解指定主键（指定自增主键生成策略，对于mysql是自增策略）
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @ApiModelProperty (hidden = true)
    private Integer id;

    @Column (unique = true, columnDefinition = "varchar(50) not null comment '登录用户名称'")// 非空约束仅第一次建表生效
    @ApiModelProperty (required = true)
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