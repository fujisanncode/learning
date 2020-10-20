EXPLAIN SELECT * FROM article_t;
SELECT * FROM empinfo;
SELECT * FROM tb_user ORDER BY update_date DESC;
INSERT INTO tb_user(user_name,update_date) VALUES('tom', SYSDATE());
INSERT INTO tb_user(user_name,user_password) SELECT user_name,user_password FROM tb_user u WHERE u.id = 13295;

# 修改列
ALTER TABLE tb_user MODIFY COLUMN id INT AUTO_INCREMENT=1;
ALTER TABLE tb_user MODIFY COLUMN update_date TIMESTAMP DEFAULT now();
# 修改自增列
ALTER TABLE tb_user auto_increment=1000;
-- 修改表名称
ALTER TABLE tb_user RENAME TO user_t;
ALTER TABLE user_t COMMENT '用户登录表';
-- 查看表状态
SHOW TABLE STATUS WHERE NAME = 'user_t';
SELECT * FROM user_t;
INSERT INTO user_t(user_name, user_password) VALUES('root', '123');
-- 从表中查询数据插入表中
INSERT INTO user_t(user_name, user_password) SELECT user_name,user_password FROM user_t u WHERE u.id=1000;
-- 复制表
CREATE TABLE copy_user_t AS SELECT * FROM user_t;
SELECT * FROM copy_user_t;

UPDATE user_t u SET u.sex = 'boy' WHERE u.id = 1000; 

-- 设置数据库安全更新 只能通过主键更新或者删除
SHOW VARIABLES LIKE 'sql_safe_updates';
SET sql_safe_updates = 1;
UPDATE user_t u SET u.sex = 'boy' WHERE u.sex= 'boy';
DELETE FROM user_t WHERE id = 1001;

-- 记录文章 浏览次数 点赞次数 等统计数据
CREATE TABLE IF NOT EXISTS article_scan_t (
	id 						INT 					PRIMARY KEY auto_increment COMMENT '浏览记录的id',
	article_id 		INT 					NOT NULL COMMENT '浏览记录关联文章id 一对一',
	scans					INT 					DEFAULT 0 COMMENT '文章浏览次数',
	votes					INT    				DEFAULT 0 COMMENT '文章被点赞次数',
	update_time 	TIMESTAMP 		DEFAULT now() COMMENT '浏览记录最后更新时间',
	update_by 		CHAR(100) 		DEFAULT 'sys' COMMENT '浏览记录最后更新人'
) auto_increment = 5 COMMENT '文章浏览记录';

-- 记录回复文章的数据
CREATE TABLE IF NOT EXISTS article_repley_t (
	id						INT						PRIMARY KEY auto_increment COMMENT '回复表的id',
	content				VARCHAR(1000) DEFAULT '' COMMENT '回复内容',
	article_id		INT						NOT NULL COMMENT '关联文章表 多对一',
	replay_id			INT						COMMENT '关联回复记录 可以为null',
	votes					INT    				DEFAULT 0 COMMENT '回复被点赞次数',
	update_time 	TIMESTAMP 		DEFAULT now() COMMENT '回复最后更新时间',
	update_by 		CHAR(100) 		DEFAULT 'sys' COMMENT '回复最后更新人'
) COMMENT '回复文章的记录';

-- 删除文章表的article_reply字段 article_votes字段 增加update_by字段 修改article_update_time字段
ALTER TABLE article_t CHANGE article_update_time update_time TIMESTAMP DEFAULT now();
ALTER TABLE article_t ADD COLUMN update_by CHAR(10) DEFAULT 'sys' COMMENT '最后更新人';
ALTER TABLE article_t DROP COLUMN article_reply, DROP COLUMN article_votes;
-- 调整字段位置 必须制定字段的类型
ALTER TABLE article_t MODIFY update_time TIMESTAMP AFTER article_status;
SELECT * from article_t;

SELECT * FROM empinfo;
DROP TABLE empinfo;

SELECT * FROM article_t ORDER BY update_time DESC;
INSERT INTO article_reply_t(content, article_id) VALUES('ok', 'fede0fecf4ae485a93f590f949048364');
INSERT INTO article_reply_t(content, article_id) VALUES('ok1', '13d9cfc450a844a88cb28648e3f42b00');
ALTER TABLE article_repley_t RENAME TO article_reply_t;
ALTER TABLE article_reply_t MODIFY COLUMN article_id CHAR(32);
SELECT * FROM article_reply_t;

-- 给回复表的article_id字段增加外键约束
ALTER TABLE article_reply_t ADD FOREIGN KEY f_article_id(article_id) REFERENCES article_t(article_id);
DELETE FROM article_reply_t WHERE article_id = 'fede0fecf4ae485a93f590f949048364'; 
-- 因为从表存在外键约束 导致主表的记录无法删除(删除主表记录 需先删除从表记录)
DELETE FROM article_t WHERE article_id = 'fede0fecf4ae485a93f590f949048364';

-- 创建视图
CREATE VIEW article_scan_v AS
 SELECT a.article_id, a.article_title, a.article_content, ar.content FROM article_t a, article_reply_t ar 
	WHERE a.article_id = ar.article_id;
-- 重命名视图只能删除后 然后重新建立视图
DROP VIEW article_scan_v;
SELECT * FROM article_scan_v;

SHOW TABLES;
ALTER TABLE copy_user_t RENAME TO user_t_c;

-- 创建存储过程
CREATE PROCEDURE query_article_p()
BEGIN
	SELECT * FROM article_t;
END
-- 调用存储过程
CALL query_article_p();

-- 1、输入参数(在存储过程中作为查询条件) 输出参数(保存变量)
DROP PROCEDURE article_update_p;

delimiter //
CREATE PROCEDURE article_update_p(
	IN limcon TIMESTAMP,
	OUT max TIMESTAMP, 
	OUT min TIMESTAMP,
	OUT max_p TIMESTAMP
) COMMENT '通过存储过程统计出符合条件的更新时间'
BEGIN
	-- 	申明局部变量
	DECLARE time_temp TIMESTAMP;
	SELECT MAX(update_time), MIN(update_time) INTO max, min FROM article_t WHERE update_time < limcon;
	-- 	使用if 进行逻辑处理
	IF limcon > '2019-07-09' THEN
		SELECT max + 1 INTO time_temp;
	END IF;
	-- 	输出局部变量
	SELECT time_temp INTO max_p;
END//
delimiter ;

SHOW PROCEDURE STATUS LIKE 'article_update_p';

SHOW CREATE PROCEDURE article_update_p;
-- 2、调用存储过程 保存结果为变量 mysql中变量必须@开头  调用时需保证参数顺序相同
CALL article_update_p('2019-07-10', @maxTime, @minTime, @maxTimePlus);
-- 3、使用变量
SELECT @maxTime, @minTime, @maxTimePlus;

-- 查询建表语句
SHOW TABLE STATUS;
SHOW CREATE TABLE article_t;
ALTER TABLE article_t MODIFY update_time TIMESTAMP DEFAULT now();
ALTER TABLE article_t MODIFY update_time TIMESTAMP DEFAULT now();
DESC article_t;
SELECT * FROM article_t WHERE article_id = '5865d982b5aa4fab801f5009eb3068f6';
UPDATE article_t t SET t.update_by = 'tom' WHERE t.article_id = '5865d982b5aa4fab801f5009eb3068f6';
INSERT INTO article_t(article_id,article_title, article_content, article_author,article_tag1,article_status) VALUES('test1', 'title', 'content', 'hulei','transaction',1);
-- 行记录被锁定 for update 会等待 nowait则抛出异常
SELECT * FROM article_t WHERE article_id = '5865d982b5aa4fab801f5009eb3068f6' FOR UPDATE nowait;

SHOW ENGINE INNODB STATUS;
SELECT * FROM information_schema.data_lock;
USE information_schema;
SELECT * FROM information_schema.INNODB_TRX;
SHOW TABLES;
USE leaning_db_t;
-- 查询锁定的事务
SELECT * FROM `performance_schema`.data_locks;

-- 事务 mysql中事务默认级别是可重复读 
-- 可重复读，事务中插入语句后select能够查询到，但是update的数据无法被查询到(事务提交后可以查询)
BEGIN; -- 开始事务
INSERT INTO article_t(article_id,article_title, article_content, article_author,article_tag1,article_status) VALUES('transaction_a', 'title', 'content', 'hulei','transaction',1);
SAVEPOINT save_a; -- 设定回滚记录点
INSERT INTO article_t(article_id,article_title, article_content, article_author,article_tag1,article_status) VALUES('transaction_b', 'title', 'content', 'hulei','transaction',1);
ROLLBACK TO save_a;  -- 回滚到指定位置
ROLLBACK; -- 回滚到事务开始的位置
COMMIT; -- 提交事务


-- 通过insert触发设置uuid
ALTER TABLE leaning_db_t.article_t MODIFY COLUMN article_id char(32) DEFAULT REPLACE(UUID(),'-','');
SELECT * FROM leaning_db_t.article_t t ORDER BY t.update_time DESC; 

SELECT * FROM `performance_schema`.data_locks;
DELETE FROM leaning_db_t.article_t WHERE article_id = 'test1';

-- 游标 查询数据集合list 然后通过通过游标在list中进一步查询或者更新
-- 游标只能在存储过程中使用
DROP PROCEDURE cursor_p;
delimiter //
CREATE PROCEDURE cursor_p()
BEGIN
	DECLARE save_id, save_title CHAR; -- 申明变量保存游标(变量必须申明在游标之前)
	DECLARE active_article_c CURSOR FOR 
		SELECT t.article_id, t.article_title FROM leaning_db_t.article_t t WHERE t.article_status = 1;  -- 声明游标
	OPEN active_article_c; -- 打开游标
	FETCH active_article_c INTO save_id, save_title; -- 取出游标中的数据
	CLOSE active_article_c; -- 关闭游标
END//
delimiter ;

DESC leaning_db_t.article_t;
DESC leaning_db_t.article_reply_t;
-- 文章回复表 增加关联外部的键
ALTER TABLE leaning_db_t.article_reply_t ADD CONSTRAINT FOREIGN KEY (article_id) REFERENCES leaning_db_t.article_t(article_id);
-- 因为外面表存在一个键值约束，因此不能删除主表的数据
DELETE FROM article_t WHERE article_id = 'fede0fecf4ae485a93f590f949048364';
SELECT * FROM article_reply_t;
SELECT * FROM article_t;
SELECT a.article_title articleTitle, a.article_content articleContent, ar.content replayContent
	FROM article_t a, article_reply_t ar
	WHERE a.article_id = ar.article_id
	AND a.article_id = 'fede0fecf4ae485a93f590f949048364';
INSERT INTO article_reply_t(content, article_id, replay_id) VALUES('replay to 2', 'fede0fecf4ae485a93f590f949048364', '2');
INSERT INTO article_reply_t(content, article_id, replay_id) VALUES('replay to 2 copy', 'fede0fecf4ae485a93f590f949048364', '2')
INSERT INTO article_reply_t(content, article_id, replay_id) VALUES('replay to 5', 'fede0fecf4ae485a93f590f949048364', '5')
INSERT INTO article_reply_t(content, article_id, replay_id, votes) VALUES('replay to 5', 'fede0fecf4ae485a93f590f949048364', '5', -1)
SELECT ar.content FROM article_reply_t ar
	WHERE ar.replay_id = '2';
-- 业务id是业务上的主键 表id是表的主键 (表id不适合作为业务id时，需要设置业务id)
ALTER TABLE article_reply_t ADD CONSTRAINT CHECK (votes >= 0);
-- 文章回复表 article_id 字段增加索引
CREATE INDEX article_inx ON article_reply_t(article_id);
-- 解释查询
EXPLAIN SELECT * FROM article_reply_t ar WHERE ar.id = '5';
EXPLAIN SELECT * FROM article_reply_t ar WHERE ar.article_id = 'fede0fecf4ae485a93f590f949048364';
EXPLAIN SELECT * FROM article_reply_t ar WHERE ar.replay_id = '5';
-- 查询索引
SELECT * FROM mysql.innodb_index_stats WHERE database_name = 'leaning_db_t' AND table_name = 'article_reply_t';
-- 通过触发器 在文章回复表插入数据的时候 自动插入主键
-- AFTER 类型触发器 后面不能更新数据  (更新数据的操作只能在BEFORE类型的触发器中)
-- INSERT 或者 UPDATE 类型的触发器中 不能插入数据(触发器中INSERT语句会不断引起触发器的触发，陷入死循环) 所以只能使用SET代替
DROP TRIGGER IF EXISTS generator_id_tri;
CREATE TRIGGER generator_id_tri BEFORE INSERT
		ON article_reply_t FOR EACH ROW
		SET NEW.id = REPLACE(UUID(), '-', '');
SELECT * FROM information_schema.`TRIGGERS`;

INSERT INTO article_reply_t(content) VALUES('测试触发器');
INSERT INTO article_reply_t(id, content) VALUES('10', '测试触发器');
SELECT * FROM leaning_db_t.article_reply_t t ORDER BY t.update_time DESC;
ALTER TABLE leaning_db_t.article_reply_t MODIFY COLUMN id CHAR(32);
DESC leaning_db_t.article_reply_t;

-- 分组统计 关联查询
SELECT * FROM article_reply_t;
SELECT a.article_title articleTitle, SUM(ar.votes) replyCounts
	FROM article_t a, article_reply_t ar 
	WHERE a.article_id = ar.article_id
	AND a.article_status = 1 GROUP BY a.article_id;

-- 供货商表 产品表 顾客表 订单表 订单明细表
CREATE TABLE IF NOT EXISTS demo_vendors_t(
	vend_id VARCHAR(32) PRIMARY KEY COMMENT '主键，唯一的供应商',
	vend_name VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '供应商名称',
	vend_address VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '供应商地址',
	vend_zip VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '供应商地址邮编',
	vend_city VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '供应商所在城市',
	vend_state VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '供应商所在州',
	vend_country VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '供应商所在国家'
) COMMENT '存储销售产品的供货商';

-- 建立外键约束fk_demo_vendors， 用列vend_id约束表demo_vendors_t
CREATE TABLE IF NOT EXISTS demo_products_t(
	prod_id VARCHAR(32) COMMENT '主键，唯一的产品id',
	prod_name VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '产品名称',
	prod_price DOUBLE NOT NULL DEFAULT 0 COMMENT '产品价格',
	prod_desc VARCHAR(1000) NOT NULL DEFAULT 'sys' COMMENT '产品描述',
	vend_id VARCHAR(32) NOT NULL DEFAULT 'sys' COMMENT '产品描述',
	PRIMARY KEY (prod_id),
	KEY fk_demo_vendors (vend_id),
	CONSTRAINT fk_demo_vendors FOREIGN KEY (vend_id) REFERENCES demo_vendors_t(vend_id)
) COMMENT '包含产品目录';

CREATE TABLE IF NOT EXISTS demo_customers_t(
	cust_id VARCHAR(32) COMMENT '主键，唯一的顾客id',
	cust_name VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '顾客名称',
	cust_address VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '顾客地址',
	cust_zip VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '顾客地址邮编',
	cust_city VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '顾客所在城市',
	cust_state VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '顾客所在州',
	cust_country VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '顾客所在国家',
	cust_contact VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '顾客联系电话',
	cust_email VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '顾客邮箱',
	PRIMARY KEY (cust_id)
) COMMENT '存储顾客信息';

DROP TABLE IF EXISTS demo_orders_t;
CREATE TABLE IF NOT EXISTS demo_orders_t(
	order_id VARCHAR(32) COMMENT '主键，唯一订单id',
	order_date TIMESTAMP DEFAULT now() COMMENT '顾客下单日期',
	cust_id VARCHAR(32) COMMENT '下单的顾客id',
	PRIMARY KEY (order_id),
	KEY fk_demo_customers (cust_id),
	CONSTRAINT fk_demo_customers FOREIGN KEY (cust_id) REFERENCES demo_customers_t(cust_id)
) COMMENT '存储顾客订单';

CREATE TABLE IF NOT EXISTS demo_order_items_t(
	order_item_id VARCHAR(32) COMMENT '主键，订单明细表唯一id',
	order_item_price DOUBLE NOT NULL DEFAULT 1 COMMENT '物品单价',
	order_item_quantity INT NOT NULL DEFAULT 1 COMMENT '物品数量',
	prod_id VARCHAR(32) NOT NULL COMMENT '产品id, 关联产品表主键',
	order_id VARCHAR(32) NOT NULL COMMENT '订单id， 关联订单表',
	PRIMARY KEY (order_item_id),
	KEY fk_demo_product (prod_id),
	CONSTRAINT fk_demo_product FOREIGN KEY (prod_id) REFERENCES demo_products_t(prod_id),
	KEY fk_demo_order (order_id),
	CONSTRAINT fk_demo_order FOREIGN KEY (order_id) REFERENCES demo_orders_t(cust_id)
) COMMENT '存储订单中的实际物品';

ALTER TABLE stu_tea_t ADD CONSTRAINT fk_stu FOREIGN KEY (stu_id) REFERENCES student_t(id);
ALTER TABLE stu_tea_t ADD CONSTRAINT fk_tea FOREIGN KEY (tea_id) REFERENCES teacher_t(id);

ALTER TABLE user_t ADD COLUMN VARCHAR(20) DEFAULT 'zh_CN' COMMENT '用户数据 语言';

SELECT 
	t.article_id,
	t.article_tag1,
	t.article_tag2
FROM
	article_t t
WHERE
	t.article_id = '5865d982b5aa4fab801f5009eb3068f6';
	
-- ===================================查看查看事务 44947
SELECT * FROM information_schema.INNODB_trx;

-- 人员信息表
CREATE TABLE IF NOT EXISTS PERSON_T(
id INT PRIMARY KEY auto_increment COMMENT '主键id',
first_name VARCHAR(10) not null COMMENT '名称',
last_name VARCHAR(10) not null COMMENT '姓氏',
address_lv1 VARCHAR(50) not null COMMENT '国家',
address_lv1_code VARCHAR(50) not null COMMENT '国家编码',
address_lv2 VARCHAR(50) not null COMMENT '省',
address_lv2_code VARCHAR(50) not null COMMENT '省编码',
address_lv3 VARCHAR(50) not null COMMENT '市',
address_lv3_code VARCHAR(50) not null COMMENT '市编码',
address_lv4 VARCHAR(500) COMMENT '详细地址'
) COMMENT '人员信息';
SELECT * FROM person_t ORDER BY id desc;
INSERT INTO person_t(first_name, last_name, address_lv1, address_lv1_code, address_lv2, address_lv2_code, address_lv3, address_lv3_code, address_lv4) VALUES('三', '张', '中国', 'CN', '江苏', 'CN-3', '南京', 'CN-3-1', '江宁区淳化街道融侨世家3-509');

-- 定义// 为语句结束标志，在存储过程中即可使用;,结束后重新设置;为结束标志
DROP PROCEDURE insert_person_p;
delimiter //
CREATE PROCEDURE insert_person_p(IN repeat_num int)
BEGIN
	DECLARE count_temp int;
	set count_temp = 0;
	WHILE count_temp < repeat_num DO
		INSERT INTO person_t(first_name, last_name, address_lv1, address_lv1_code, address_lv2, address_lv2_code, address_lv3, address_lv3_code, 
		address_lv4) VALUES('三', '张', '中国', 'CN', '江苏', 'CN-3', '南京', 'CN-3-1', '江宁区淳化街道融侨世家3-509');
		set count_temp = count_temp + 1;
	END WHILE;
END;
//
delimiter ;
-- 调用存储过程
CALL insert_person_p(10000);
COMMIT;

SELECT * FROM person_t ORDER BY id desc
WHERE last_update_time is not null
ORDER BY last_update_time desc;
-- 关闭自动提交，避免存储过程每次循环提交事务，提高插入数据效率
SET @@global.autocommit = 0; 
SHOW GLOBAL VARIABLES LIKE 'autocommit'; -- 全局变量
SET @@SESSION.autocommit = 0;
SHOW SESSION VARIABLES LIKE 'autocommit'; -- 当前session变量
SHOW PROCESSLIST;


-- 修改表字符集和排序规则
alter TABLE person_t CONVERT to CHARACTER set utf8 COLLATE utf8_general_ci;

select 
	*
from
	person_t p
where 1=1
	and EXISTS(
		select 
			r1.`name`
		FROM
			region_lv1_t r1
		WHERE 1=1
			and p.address_lv1_code = 'CN'
			and p.address_lv2_code = r1.code
	)
	LIMIT 10;
	
-- GROUP BY 和 sum
WITH
r1 AS (
	SELECT * FROM region_lv1_t
)
SELECT
	r1.updator,
	COUNT(r1.updator)
FROM
	r1
WHERE
	r1.`name` NOT IN ('北京', '上海') -- 不统计北京和上海
GROUP BY
	r1.updator -- 按照updator分组
HAVING 1=1
	and r1.updator = 'All' -- 分组后只要All组数据
	and COUNT(r1.updator) > 5 -- 并且All组数量大于5

SELECT VERSION()

-- 查询数据库密码
select * from user where user = 'root';
select SYSDATE();
SELECT COUNT(1) FROM person_t;

-- mysql8 cte公用表达式
with temp as (
select * from region_lv1_t
)
select * from person_t, temp
where 1=1
and temp.name = person_t.address_lv2
LIMIT 1

SELECT COUNT(1) FROM person_t;

-- 用户 角色 权限表
-- --> step0
SHOW TABLES;

-- --> step1
DROP TABLE IF EXISTS permission_t;
DROP TABLE IF EXISTS role_t;
DROP TABLE IF EXISTS login_t;

-- --> step2
CREATE TABLE IF NOT EXISTS login_t(
	id INT(32) PRIMARY KEY auto_increment COMMENT '用户表主键id',
	name VARCHAR(500) NOT NULL DEFAULT 'sys' COMMENT '用户名称',
	password VARCHAR(500) NOT NULL DEFAULT '123' COMMENT '用户密码',
	create_by VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	create_time TIMESTAMP NOT NULL DEFAULT now() COMMENT '创建时间',
 	update_by VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '最后更新人',
	update_time TIMESTAMP NOT NULL DEFAULT now() COMMENT '最后更新时间'
) ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT '登录用户表';

CREATE TABLE IF NOT EXISTS role_t(
	id INT(32) PRIMARY KEY auto_increment COMMENT '角色表主键id',
	name VARCHAR(500) NOT NULL DEFAULT 'admin' COMMENT '角色名称',
	login_id INT(32) NOT NULL DEFAULT '1' COMMENT '关联权限表主键id',
	CONSTRAINT fk_role_user_1 FOREIGN KEY(login_id) REFERENCES login_t(id),
	create_by VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	create_time TIMESTAMP NOT NULL DEFAULT now() COMMENT '创建时间',
 	update_by VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '最后更新人',
	update_time TIMESTAMP NOT NULL DEFAULT now() COMMENT '最后更新时间'
) ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT '角色表->多个角色对应一个用户（外键）';

CREATE TABLE IF NOT EXISTS permission_t(
	id INT(32) PRIMARY KEY auto_increment COMMENT '权限表主键id',
	name VARCHAR(500) NOT NULL DEFAULT 'all' COMMENT '权限名称',
	role_id INT(32) NOT NULL DEFAULT '1' COMMENT '关联角色表主键id',
	CONSTRAINT fk_permission_role_1 FOREIGN KEY(role_id) REFERENCES role_t(id),
	create_by VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	create_time TIMESTAMP NOT NULL DEFAULT now() COMMENT '创建时间',
 	update_by VARCHAR(50) NOT NULL DEFAULT 'sys' COMMENT '最后更新人',
	update_time TIMESTAMP NOT NULL DEFAULT now() COMMENT '最后更新时间'
) ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT '权限表->多个权限对应一个角色（外键）';

-- --> step3
INSERT INTO login_t(id, `name`) VALUES(1, 'sys');
INSERT INTO role_t(`login_id`) VALUES('1');
INSERT INTO permission_t(`role_id`) VALUES('1');

-- --> ste4
SELECT
	ut.id,
	ut.`name`,
	ut.`password`,
	rt.id,
	rt.`name`,
	pt.id,
	pt.`name`
FROM 
	login_t ut,
	role_t rt,
	permission_t pt
where 1=1
	AND pt.role_id = rt.id
	AND rt.login_id = ut.id
	AND ut.`name` = 'sys';
	
SELECT * FROM region_lv1_t r1 WHERE r1.id = 10 FOR UPDATE nowait;
COMMIT;
UPDATE region_lv1_t SET updator = 'sys' WHERE id = 10;






























