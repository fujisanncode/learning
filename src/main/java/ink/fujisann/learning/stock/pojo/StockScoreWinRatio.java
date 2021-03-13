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
@Table(name = "stock_score_win_ratio")
@org.hibernate.annotations.Table(appliesTo = "stock_score_win_ratio", comment = "股票评分系统胜率")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class StockScoreWinRatio extends BaseInfo {
    @Id
    @GenericGenerator(name = "stock_score_win_ratio_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "stock_score_win_ratio_id")
    @ApiModelProperty("主键")
    private String id;

    @Column(columnDefinition = "varchar(16) default null comment '股票编码'")
    @ApiModelProperty("股票编码")
    private String tsCode;

    @Column(columnDefinition = "int default null comment '胜利股票数量'")
    @ApiModelProperty("胜利股票数量")
    private String winCount;

    @Column(columnDefinition = "decimal(10, 2) default null comment '胜利股票占比'")
    @ApiModelProperty("胜利股票占比")
    private String winCountPct;

    @Column(columnDefinition = "decimal(10, 2) default null comment '推荐股票当日总计涨跌幅度'")
    @ApiModelProperty("推荐股票当日总计涨跌幅度")
    private String totalChangePct;

    @Column(columnDefinition = "text default null comment '个股胜率明细(json字符串)'")
    @ApiModelProperty("个股胜率明细")
    private String radioDetail;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default null comment '股票交易日期'")
    @ApiModelProperty("股票交易日期")
    private Date tradeDate;
}
