package ink.fujisann.learning.code.pojo.plan;

import ink.fujisann.learning.code.pojo.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 计划以及完成情况
 *
 * @author hulei
 * @upate 2020-05-27 19:14:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "plan_t")
@DynamicInsert
@DynamicUpdate
@Builder // builder会覆盖构造，需要指定生成无参构造
@NoArgsConstructor
@AllArgsConstructor
public class Plan extends BaseInfo {

  @Id
  @GenericGenerator(name = "plan_t_id", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "plan_t_id")
  @ApiModelProperty(hidden = true)
  private String id;

  @Column(columnDefinition = "varchar(300) not null comment '计划标题'")
  private String title;

  @Column(columnDefinition = "text comment '计划内容'")
  private String Content;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(columnDefinition = "timestamp default now() comment '计划开始时间'")
  private Date startTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(columnDefinition = "timestamp default now() comment '计划结束时间'")
  private Date endTime;

  @Column(columnDefinition = "int not null comment '计划内容'")
  private Integer completionRate;
}
