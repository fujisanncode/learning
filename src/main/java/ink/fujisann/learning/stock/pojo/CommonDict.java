package ink.fujisann.learning.stock.pojo;

import ink.fujisann.learning.code.pojo.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "common_dict")
@org.hibernate.annotations.Table(appliesTo = "common_dict", comment = "数据字典")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonDict extends BaseInfo {
    @Id
    @GenericGenerator(name = "common_dict_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "common_dict_id")
    @ApiModelProperty("主键")
    private String id;

    @Column(columnDefinition = "varchar(64) default null comment '字典索引key'")
    @ApiModelProperty("字典索引key")
    private String dictKey;

    @Column(columnDefinition = "varchar(64) default null comment '字典索引value'")
    @ApiModelProperty("字典索引value")
    private String dictValue;

    @Column(columnDefinition = "varchar(64) default null comment '字典值key'")
    @ApiModelProperty("字典值key")
    private String contentKey;

    @Column(columnDefinition = "varchar(64) default null comment '字典值value'")
    @ApiModelProperty("字典值value")
    private String contentValue;

}
