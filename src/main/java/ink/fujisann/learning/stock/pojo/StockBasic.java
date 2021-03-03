package ink.fujisann.learning.stock.pojo;

import ink.fujisann.learning.code.pojo.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stock_basic")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class StockBasic extends BaseInfo {
    @Id
    @GenericGenerator(name = "stock_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "stock_id")
    @ApiModelProperty("主键")
    private String id;

    @Column(columnDefinition = "varchar(16) default null comment '股票编码'")
    @ApiModelProperty("股票编码")
    private String tsCode;

    @Column(columnDefinition = "varchar(32) default null comment '股票名称'")
    @ApiModelProperty("股票名称")
    private String name;

    @Column(columnDefinition = "varchar(16) default null comment '上市地点'")
    @ApiModelProperty("上市地点")
    private String area;

    @Column(columnDefinition = "varchar(32) default null comment '所属行业'")
    @ApiModelProperty("所属行业")
    private String industry;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default null comment '上市日期'")
    @ApiModelProperty("上市日期")
    private Date listDate;

}
