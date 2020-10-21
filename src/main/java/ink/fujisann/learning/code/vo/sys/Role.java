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
 * 系统用户的角色表，和用户多对多，和权限点多对多
 */
@Entity// 如果数据库不存在表,数据库会重新生成;否则改动表结构,数据库中表不会进行更新
@Table (name = "sys_role_t")
@DynamicInsert //insert的sql中，忽略null字段，否则会将对象中的null写入数据库；入库数据库字段需要空，设置为空字符串
@DynamicUpdate
@org.hibernate.annotations.Table (appliesTo = "sys_role_t", comment = "系统角色表")// appliesTo不能使用大写表名
@Data
@EqualsAndHashCode (callSuper = true)
public class Role extends BaseInfo {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @ApiModelProperty (hidden = true)
    private Integer id;

    @Column (unique = true, columnDefinition = "VARCHAR(50) DEFAULT 'admin' COMMENT '角色名称'")
    @ApiModelProperty (required = true)
    private String name;

    // 一对多的关系，由多端负责维护，一端不能删除（一端主键会作为多端表中的外键）
    // optional表示login必填（不是可选而是必填）
    // 多端不设置级联，即多端ddl不能影响一端的数据
    // @ManyToOne (cascade = CascadeType.ALL, optional = false)
    // 设置外键名称和映射字段
    // @ManyToOne
    // @JsonBackReference
    // @JoinColumn (name = "user_id")
    // private User user;
    //
    // @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
    // private List<Permission> permissions;
}
