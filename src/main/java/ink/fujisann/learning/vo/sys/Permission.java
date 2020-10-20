package ink.fujisann.learning.vo.sys;

import ink.fujisann.learning.vo.BaseInfo;
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
 * 系统角色对应的权限点表，和角色多对多
 */
@Entity// 如果数据库不存在表,数据库会重新生成;否则改动表结构,数据库中表不会进行更新
@Table (name = "sys_permission_t")
@DynamicInsert //insert的sql中，忽略null字段，否则会将对象中的null写入数据库；入库数据库字段需要空，设置为空字符串
@DynamicUpdate
@org.hibernate.annotations.Table (appliesTo = "sys_permission_t", comment = "系统接口权限点")// appliesTo不能使用大写表名
@Data
@EqualsAndHashCode (callSuper = true)
public class Permission extends BaseInfo {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @ApiModelProperty (hidden = true)
    private Integer id;

    @Column (unique = true, columnDefinition = "VARCHAR(50) DEFAULT 'admin' COMMENT '权限名称'")
    @ApiModelProperty (required = true)
    private String name;

}
