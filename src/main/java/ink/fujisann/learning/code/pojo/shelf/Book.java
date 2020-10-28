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
 * 书架 - 图书实体类
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
@Table(name = "book_t")
@org.hibernate.annotations.Table(appliesTo = "book_t", comment = "图书实体类")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseInfo {

    /**
     * {@code @GenericGenerator} 提供主键生成器
     * {@code @GeneratedValue} 指定主键生成策略，应用主键生成器
     */
    @ApiModelProperty(value = "图书主键", hidden = true)
    @GenericGenerator(name = "book_t_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "book_t_id")
    @Id
    @Column(columnDefinition = "varchar(64) not null comment '主键'")
    private String bookId;

    @ApiModelProperty("图书名称")
    @Column(name = "bookName", columnDefinition = "varchar(128) null comment '图书名称'")
    private String bookName;

    @ApiModelProperty("图书访问路径")
    @Column(name = "accessPath", columnDefinition = "varchar(511) null comment '图书访问路径'")
    private String accessPath;

    @ApiModelProperty("上传图书的用户id")
    @Column(name = "userId", columnDefinition = "varchar(32) null comment '上传图书的用户id'")
    private String userId;

    @ApiModelProperty("阅读进度,页码")
    @Column(name = "curPage", columnDefinition = "int not null default 0 comment '上传图书的用户id'")
    private Integer curPage;
}
