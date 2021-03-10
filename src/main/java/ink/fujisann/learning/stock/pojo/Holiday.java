package ink.fujisann.learning.stock.pojo;

import ink.fujisann.learning.code.pojo.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "holiday")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
public class Holiday extends BaseInfo {
    @Id
    @GenericGenerator(name = "holiday_id", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "holiday_id")
    @ApiModelProperty("主键")
    private String id;

    @Column(columnDefinition = "varchar(16) default null comment '假期名称'")
    @ApiModelProperty("假期名称")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default null comment '假期日期'")
    @ApiModelProperty("假期日期")
    private Date date;
}
