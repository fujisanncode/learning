package com.example.sqllearning.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass //公用属性
@Data// 父类必须提供set，get方法；或者属性定义为public；否则子类不能访问父类的任何属性
public class BaseInfo implements Serializable {

    @Temporal (TemporalType.TIMESTAMP)
    @Column (columnDefinition = "TIMESTAMP DEFAULT NOW() COMMENT '创建时间'")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss") // 入参解析
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss") // 返回序列化
    @ApiModelProperty (hidden = true)
    private Date createTime;

    @Column (columnDefinition = "VARCHAR(50) DEFAULT 'sys' COMMENT '创建人'")
    @ApiModelProperty (hidden = true)
    private String createBy;

    @Column (columnDefinition = "VARCHAR(50) DEFAULT 'sys' COMMENT '最后更新人'")
    @ApiModelProperty (hidden = true)
    private String updateBy;

    @Temporal (TemporalType.TIMESTAMP)
    @Column (columnDefinition = "TIMESTAMP DEFAULT NOW() COMMENT '最后更新时间'")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss") // 入参解析
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss") // 返回序列化
    @ApiModelProperty (hidden = true)
    private Date updateTime;
}
