package ink.fujisann.learning.code.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

/**
 * 存储到mongo的blog
 *
 * @author hulei
 * @date 2020-11-10 23:22:23:22
 */
@Data
@ApiModel(description = "存储到mongo的blog")
@Document(collection = "mongo_blog")
public class MongoBlog {

    @ApiModelProperty("主键")
    @Id
    private String id;

    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客内容")
    private String content;

    @ApiModelProperty("博客所有者")
    private String author;
    
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
