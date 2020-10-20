# delete from hibernate_sequence;
# delete from sys_user_role_t;
# delete from sys_role_permission_t;
# delete from sys_user_t;
# delete from sys_role_t;
# delete from sys_permission_t;
insert into sys_user_t(id, name, password)
values ('1', 'hulei', '123');
insert into sys_role_t(id, name)
values ('2', 'admin');
insert into sys_user_role_t(id, role_id, user_id)
values ('3', '2', '1');
insert into sys_permission_t(id, name)
values ('4', '/shiro-manage/addExistPermission');
insert into sys_permission_t(id, name)
values ('5', '/shiro-manage/findNonPermissionByRole');
insert into sys_permission_t(id, name)
values ('6', '/shiro-manage/addRolePermissionBatch');
insert into sys_permission_t(id, name)
values ('7', '/shiro-manage/findAllUser');
insert into sys_permission_t(id, name)
values ('8', '/shiro-manage/findAllRole');
insert into sys_permission_t(id, name)
values ('9', '/shiro-manage/findAllPermission');
insert into sys_permission_t(id, name)
values ('10', '/shiro-manage/findRoleByUser');
insert into sys_permission_t(id, name)
values ('11', '/shiro-manage/findPermissionByRole');
insert into sys_role_permission_t(id, permission_id, role_id)
values ('12', '4', '2');
insert into sys_role_permission_t(id, permission_id, role_id)
values ('13', '5', '2');
insert into sys_role_permission_t(id, permission_id, role_id)
values ('14', '6', '2');
insert into sys_role_permission_t(id, permission_id, role_id)
values ('15', '7', '2');
insert into sys_role_permission_t(id, permission_id, role_id)
values ('16', '8', '2');
insert into sys_role_permission_t(id, permission_id, role_id)
values ('17', '9', '2');
insert into sys_role_permission_t(id, permission_id, role_id)
values ('18', '10', '2');
insert into sys_role_permission_t(id, permission_id, role_id)
values ('19', '11', '2');