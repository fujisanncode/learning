package ink.fujisann.learning.code.pojo.mybatis;

import lombok.Data;

import java.util.Date;

@Data
public class RegionLv1 {

    private Integer id;

    private String code;

    private String name;

    private String remark;

    private String updator;

    private Date updateTime;

    private Integer relationId;
}