# 表中存在外键约束，不能使用truncate
set foreign_key_checks = 0;
truncate sys_user_t;
truncate sys_user_role_t;
truncate sys_role_t;
truncate sys_role_permission_t;
truncate sys_permission_t;
set foreign_key_checks = 1;
