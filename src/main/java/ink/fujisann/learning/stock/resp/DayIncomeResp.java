package ink.fujisann.learning.stock.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DayIncomeResp {

    @ApiModelProperty("收入类型名称")
    private String incomeName;

    @ApiModelProperty("当日涨跌额")
    private BigDecimal dayIncrease;

    @ApiModelProperty("累计涨跌幅")
    private BigDecimal cumulativeIncrease;

    @ApiModelProperty("总投入")
    private BigDecimal investment;

    @ApiModelProperty("剩余资金")
    private BigDecimal investmentRemain;

    @ApiModelProperty("当前日期")
    private Date day;

    @ApiModelProperty("币种")
    private String currency;
}
