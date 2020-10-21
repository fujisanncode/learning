package ink.fujisann.learning.code.vo.data;

import ink.fujisann.learning.code.vo.BaseInfo;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @description: 地理数据接口
 * @author: hulei
 * @create: 2020-06-28 20:42:08
 */
@Entity
@Table(name = "geo_t")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Geo extends BaseInfo {
  @Id
  @Column(columnDefinition = "varchar(32) not null comment '主键id'")
  @GenericGenerator(name = "geoId", strategy = "uuid")
  @GeneratedValue(generator = "geoId")
  private String id;

  @Column(columnDefinition = "varchar(20) not null comment '区域父层级编码'")
  private String parentCode;

  @Column(columnDefinition = "varchar(10) not null comment '地区类型编码'")
  private String areaType;

  @Column(columnDefinition = "varchar(20) not null comment '地区编码'")
  private String areaCode;

  @Column(columnDefinition = "varchar(100) not null comment '地区名称'")
  private String areaName;

  @Column(columnDefinition = "varchar(100) comment '地区经纬度'")
  private String geo;
}
