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
@Table(name = "stock_score_detail")
@org.hibernate.annotations.Table(appliesTo = "stock_score_detail", comment = "股票各项得分明细")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class StockScoreDetail extends BaseInfo {
    @Id
    @GenericGenerator(name = "stock_score_detail_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "stock_score_detail_id")
    @ApiModelProperty("主键")
    private String id;

    @Column(columnDefinition = "varchar(16) default null comment '股票编码'")
    @ApiModelProperty("股票编码")
    private String tsCode;

    @Column(columnDefinition = "varchar(255) default null comment '评分因子Id'")
    @ApiModelProperty("评分因子Id")
    private String factorId;

    @Column(columnDefinition = "decimal(10, 2) default null comment '得分系数(最大1)'")
    @ApiModelProperty("得分系数(最大1)")
    private String scoreCoefficient;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default null comment '计算日期(计算结果用于预测下一个交易日涨跌)'")
    @ApiModelProperty("计算日期(计算结果用于预测下一个交易日涨跌)")
    private Date calculatorDate;
}
