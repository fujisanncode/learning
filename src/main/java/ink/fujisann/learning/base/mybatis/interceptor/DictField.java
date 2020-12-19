package ink.fujisann.learning.base.mybatis.interceptor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("保存需要查询字典的字段名称")
@Data
public class DictField {
    @ApiModelProperty("valueCode字段的名称")
    private String valueCodeFieldName;
    @ApiModelProperty("valueName字段的名称")
    private String valueNameFieldName;
    @ApiModelProperty("typeCode的值")
    private String typeCode;
}
