package ink.fujisann.learning.vo.mybatis;

import java.util.Date;
import lombok.Data;

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