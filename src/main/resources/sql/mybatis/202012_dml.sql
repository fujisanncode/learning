# 客户表内置数据
insert into learning.customer_t
values (1, 'C001', 'fujisann', '1', now(), now());

# 字典表
insert into learning.common_dict_t
values (1, 'customer_type', '客户类型', '1', '月租客户', now(), now());