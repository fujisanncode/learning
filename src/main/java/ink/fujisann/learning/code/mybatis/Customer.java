package ink.fujisann.learning.code.mybatis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * 客户表
 */
@ApiModel(value = "ink-fujisann-learning-code-pojo-mybatis-Customer")
@Data
@TableName(value = "learning.customer_t")
public class Customer {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 编号
     */
    @TableField(value = "userNum")
    @ApiModelProperty(value = "编号")
    private String userNum;

    /**
     * 姓名
     */
    @TableField(value = "userName")
    @ApiModelProperty(value = "姓名")
    private String userName;

    /**
     * 客户类型id
     */
    @TableField(value = "customerTypeId")
    @ApiModelProperty(value = "客户类型id")
    private String customerTypeId;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}