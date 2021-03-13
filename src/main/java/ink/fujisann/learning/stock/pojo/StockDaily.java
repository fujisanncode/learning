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
@Table(name = "stock_daily")
@org.hibernate.annotations.Table(appliesTo = "stock_daily", comment = "A股日K行情")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class StockDaily extends BaseInfo {
    @Id
    @GenericGenerator(name = "stock_daily_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "stock_daily_id")
    @ApiModelProperty("主键")
    private String id;

    @Column(columnDefinition = "varchar(16) default null comment '股票编码'")
    @ApiModelProperty("股票编码")
    private String tsCode;

    @Column(columnDefinition = "decimal(10,2) default null comment '开盘价'")
    @ApiModelProperty("开盘价")
    private BigDecimal open;

    @Column(columnDefinition = "decimal(10,2) default null comment '最高价'")
    @ApiModelProperty("最高价")
    private BigDecimal high;

    @Column(columnDefinition = "decimal(10,2) default null comment '最低价'")
    @ApiModelProperty("最低价")
    private BigDecimal low;

    @Column(columnDefinition = "decimal(10,2) default null comment '收盘价'")
    @ApiModelProperty("收盘价")
    private BigDecimal close;

    @Column(columnDefinition = "decimal(10,2) default null comment '昨日收盘价'")
    @ApiModelProperty("昨日收盘价")
    private BigDecimal preClose;

    @Column(name = "\"change\"", columnDefinition = "decimal(10,2) default null comment '昨日收盘价'")
    @ApiModelProperty("昨日收盘价")
    private BigDecimal change;

    @Column(columnDefinition = "decimal(10,2) default null comment '涨跌额(未复权)'")
    @ApiModelProperty("涨跌额(未复权)")
    private BigDecimal pctChg;

    @Column(columnDefinition = "decimal(10,2) default null comment '成交量(手'")
    @ApiModelProperty("成交量(手)")
    private BigDecimal vol;

    @Column(columnDefinition = "decimal(10,2) default null comment '成交额(千元)'")
    @ApiModelProperty("成交额(千元)")
    private BigDecimal amount;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default null comment '交易日期'")
    @ApiModelProperty("交易日期")
    private Date tradeDate;

}
