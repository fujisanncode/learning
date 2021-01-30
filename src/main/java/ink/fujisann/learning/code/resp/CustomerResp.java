package ink.fujisann.learning.code.resp;

import ink.fujisann.learning.base.mybatis.annotation.DictValue;
import ink.fujisann.learning.base.mybatis.annotation.NeedDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 客户查询
 *
 * @author raiRezon
 * @version 2020/12/19
 */
@NeedDict
@Data
@ApiModel(description = "客户查询")
public class CustomerResp {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "编号")
    private String userNum;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @DictValue(typeCode = "customer_type", valueNameField = "customerTypeValue")
    @ApiModelProperty(value = "客户类型id")
    private String customerTypeId;

    @ApiModelProperty(value = "客户类型值")
    private String customerTypeValue;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
