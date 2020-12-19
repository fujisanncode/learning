package ink.fujisann.learning.code.pojo.mybatis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
    * 字典表
    */
@ApiModel(value="ink-fujisann-learning-code-pojo-mybatis-CommonDict")
@Data
@TableName(value = "learning.common_dict_t")
public class CommonDict {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 类编码
     */
    @TableField(value = "typeCode")
    @ApiModelProperty(value="类编码")
    private String typeCode;

    /**
     * 类内容
     */
    @TableField(value = "typeValue")
    @ApiModelProperty(value="类内容")
    private String typeValue;

    /**
     * 值编码
     */
    @TableField(value = "valueCode")
    @ApiModelProperty(value="值编码")
    private String valueCode;

    /**
     * 值内容
     */
    @TableField(value = "valueValue")
    @ApiModelProperty(value="值内容")
    private String valueValue;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;
}