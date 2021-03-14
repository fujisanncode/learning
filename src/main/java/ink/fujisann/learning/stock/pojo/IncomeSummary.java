package ink.fujisann.learning.stock.pojo;

import ink.fujisann.learning.code.pojo.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "income_summary")
@org.hibernate.annotations.Table(appliesTo = "income_summary", comment = "收入汇总")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class IncomeSummary extends BaseInfo {
    @Id
    @GenericGenerator(name = "income_summary_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "income_summary_id")
    @ApiModelProperty("主键")
    private String id;

    @Column(columnDefinition = "varchar(64) default null comment '收入类型'")
    @ApiModelProperty("收入类型")
    private String incomeType;

    @Column(columnDefinition = "decimal(10, 2) default null comment '总投入'")
    @ApiModelProperty("总投入")
    private BigDecimal investment;

    @Column(columnDefinition = "decimal(10, 2) default null comment '投入剩余'")
    @ApiModelProperty("投入剩余")
    private BigDecimal investmentRemain;

    @Column(columnDefinition = "decimal(10, 2) default null comment '当日涨跌额'")
    @ApiModelProperty("当日涨跌额")
    private BigDecimal dayIncrease;

    @Column(columnDefinition = "decimal(10, 2) default null comment '累计涨跌额'")
    @ApiModelProperty("累计涨跌额")
    private BigDecimal cumulativeIncrease;

    @Column(columnDefinition = "varchar(1) default null comment '币种'")
    @ApiModelProperty("币种")
    private String currency;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default null comment '记录日期'")
    @ApiModelProperty("记录日期")
    private Date day;

}
