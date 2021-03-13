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

@Entity
@Table(name = "stock_score_factor")
@org.hibernate.annotations.Table(appliesTo = "stock_score_factor", comment = "股票评分因子表")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class StockScoreFactor extends BaseInfo {
    @Id
    @GenericGenerator(name = "stock_score_factor_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "stock_score_factor_id")
    @ApiModelProperty("主键")
    private String id;

    @Column(columnDefinition = "varchar(16) default null comment '评分因子名称'")
    @ApiModelProperty("评分因子名称")
    private String factorName;

    @Column(columnDefinition = "decimal(10, 2) default null comment '评分因子比重(评分因子最大分值)'")
    @ApiModelProperty("评分因子比重(评分因子最大分值)")
    private BigDecimal factorWeight;

    @Column(columnDefinition = "text default null comment '评分因子描述'")
    @ApiModelProperty("评分因子描述")
    private String factorDesc;

}
