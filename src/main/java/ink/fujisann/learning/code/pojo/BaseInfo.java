package ink.fujisann.learning.code.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * jpa表公共字段
 * {@code @MappedSuperclass} 用于设置公共属性<br/>
 * {@code @Data} 设置父类的get、set，否则子类不能访问父类属性<br/>
 *
 * @author hulei
 * @date 2020-10-24 15:37:06
 */

@MappedSuperclass
@Data
public class BaseInfo implements Serializable {

    @Column(columnDefinition = "varchar(50) default 'sys' comment '创建人'")
    @ApiModelProperty(hidden = true)
    private String createBy;

    @Column(columnDefinition = "varchar(50) default 'sys' comment '更新人'")
    @ApiModelProperty(hidden = true)
    private String updateBy;

    /**
     * {@code @DateTimeFormat} 按格式解析入参
     * {@code @JsonFormat} 按格式返回数据
     */
    @Temporal(TemporalType.TIME)
    @Column(columnDefinition = "datetime not null default current_timestamp comment '创建时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    @Temporal(TemporalType.TIME)
    @Column(columnDefinition = "datetime not null default current_timestamp on update current_timestamp comment '更新时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden = true)
    private Date updateTime;
}
