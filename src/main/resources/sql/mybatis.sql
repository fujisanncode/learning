drop table if exists learning.customer_t;
create table if not exists learning.customer_t
(
    id         int         not null auto_increment comment '主键',
    userNum    varchar(32) null comment '编号',
    userName   varchar(32) null comment '姓名',
    createTime datetime    not null default current_timestamp comment '创建时间',
    updateTime datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) engine = innodb
  default charset = utf8mb4 comment '客户表';
insert into learning.customer_t(userNum, userName)
values (1, 'fujisann');