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
@Table(name = "stock_score_total")
@org.hibernate.annotations.Table(appliesTo = "stock_score_total", comment = "股票总得分(根据各项得分生成)")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class StockScoreTotal extends BaseInfo {

    @Id
    @GenericGenerator(name = "stock_score_total_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "stock_score_total_id")
    @ApiModelProperty("主键")
    private String id;

    @Column(columnDefinition = "varchar(16) default null comment '股票编码'")
    @ApiModelProperty("股票编码")
    private String tsCode;

    @Column(columnDefinition = "decimal(10, 2) default null comment '股票当日总分'")
    @ApiModelProperty("股票当日总分")
    private String totalScore;

    @Column(columnDefinition = "text default null comment '股票当日各项得分明细(json字符串)'")
    @ApiModelProperty("股票当日各项得分明细(json字符串)")
    private String calculateDetail;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default null comment '应用日期(用于预测哪一天的行情)'")
    @ApiModelProperty("应用日期(用于预测哪一天的行情)")
    private Date applyDate;
}
