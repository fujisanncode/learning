# 客户表
drop table if exists learning.customer_t;
create table if not exists learning.customer_t
(
    id             int         not null auto_increment comment '主键',
    userNum        varchar(32) null comment '编号',
    userName       varchar(32) null comment '姓名',
    customerTypeId varchar(32) null comment '客户类型id',
    createTime     datetime    not null default current_timestamp comment '创建时间',
    updateTime     datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) engine = innodb
  default charset = utf8mb4 comment '客户表';

# 字典表
drop table if exists learning.common_dict_t;
create table if not exists learning.common_dict_t
(
    id         int         not null auto_increment comment '主键',
    typeCode   varchar(32) null comment '类编码',
    typeValue  varchar(32) null comment '类内容',
    valueCode  varchar(32) null comment '值编码',
    valueValue varchar(32) null comment '值内容',
    createTime datetime    not null default current_timestamp comment '创建时间',
    updateTime datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) engine = innodb
  default charset = utf8mb4 comment '字典表';