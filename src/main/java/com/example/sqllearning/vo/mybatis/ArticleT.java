package com.example.sqllearning.vo.mybatis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@Data
@ApiModel (value = "ArticleT", description = "查询文章表 更新文章表")
public class ArticleT {

    @ApiModelProperty (name = "文章主键", example = "5865d982b5aa4fab801f5009eb3068f6")
    private String articleId;
    @ApiModelProperty (name = "文章标题", example = "四川的美食", required = true)
    private String articleTitle;
    @ApiModelProperty (name = "文章内容", example = "舌尖上的中国，关于四川美食...", required = true)
    private String articleContent;
    @ApiModelProperty (name = "文章作者")
    private String articleAuthor;
    @ApiModelProperty (name = "文章标签1", example = "美食")
    private String articleTag1;
    @ApiModelProperty (name = "文章标签2", example = "地理")
    private String articleTag2;
    @ApiModelProperty (name = "文章状态", example = "1", allowableValues = "0, 1")
    private Integer articleStatus;
    @ApiModelProperty (name = "文章更新时间")
    private Date articleUpdateTime;
    @ApiModelProperty (name = "文章更新人")
    private String updateBy;
}