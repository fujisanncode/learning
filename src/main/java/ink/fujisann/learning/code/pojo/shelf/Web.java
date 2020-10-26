package ink.fujisann.learning.code.pojo.shelf;

import ink.fujisann.learning.code.pojo.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 网站实体类
 * {@code @Entity} 标记这个类是数据库实体类
 * {@code @javax.persistence.Table} 指定生成的表名
 * {@code @org.hibernate.annotations.Table} 给指定表写注释
 * {@code @DynamicInsert} insert语句忽略对象中null字段
 * {@code @DynamicUpdate} update语句忽略对象中null字段
 * {@code @EqualsAndHashCode} 继承父类需要指定hashcode和equals的策略
 *
 * @author hulei
 * @date 2020-10-24 15:16:15:16
 */
@Entity
@Table(name = "web_t")
@org.hibernate.annotations.Table(appliesTo = "web_t", comment = "网站实体类")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class Web extends BaseInfo {

    /**
     * {@code @GenericGenerator} 提供主键生成器
     * {@code @GeneratedValue} 指定主键生成策略，应用主键生成器
     */
    @ApiModelProperty(value = "主键", hidden = true)
    @GenericGenerator(name = "web_t_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "web_t_id")
    @Id
    @Column(columnDefinition = "varchar(32) not null comment '主键'")
    private String webId;

    @ApiModelProperty("网站名称")
    @Column(name = "webName", columnDefinition = "varchar(32) null comment '网站名称'")
    private String webName;

    @ApiModelProperty("网站url")
    @Column(name = "webUrl", columnDefinition = "varchar(511) null comment '网站url'")
    private String webUrl;
}
