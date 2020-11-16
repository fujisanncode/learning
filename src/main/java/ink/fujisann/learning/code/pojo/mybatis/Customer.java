package ink.fujisann.learning.code.pojo.mybatis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 客户表
 *
 * @author raiRezon
 * @version 2020/11/16
 */
@Data
@ApiModel(description = "客户表")
public class Customer {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("编号")
    private String userNum;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}