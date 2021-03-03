package ink.fujisann.learning.code.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 分页
 * @author: hulei
 * @create: 2020-08-27 21:52:42
 */
@Data
@ApiModel(description = "分页")
public class PageReq {
  @ApiModelProperty("当前页")
  private int pageNum;

  @ApiModelProperty("每页数据总数")
  private int pageSize;
}
