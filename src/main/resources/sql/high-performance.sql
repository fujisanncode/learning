-- 连接到aliyun服务器
EXPLAIN SELECT * FROM test_data_s.plan_t;
EXPLAIN SELECT * FROM test_data_s.plan_t pt WHERE pt.title LIKE '%redis%';
-- 枚举类型实际存储的是正数，查询的时候从映射表中查询字符串
CREATE TABLE test_enum(
	e enum('dog', 'cat', 'mouse') not null
)
INSERT INTO test_data_s.test_enum(e) VALUES('dog'),('cat');
INSERT INTO test_data_s.test_enum(e) VALUES('do');
INSERT INTO test_data_s.test_enum(e) VALUES('mouse');
-- 默认按照存储的整数排序
EXPLAIN SELECT e+0, e FROM test_data_s.test_enum e ORDER BY e DESC;
-- 通过filed函数，指定按照字符串排序，会导致索引失效
EXPLAIN SELECT e+0, e FROM test_data_s.test_enum e ORDER BY FIELD(e, 'cat','dog', 'mouse');

SELECT * FROM test_data_s.plan_t pt;
SELECT COUNT(1) FROM test_data_s.plan_t pt;
WHERE pt.create_time > CONCAT(LEFT(now(),14) , '00:00');
-- 查詢最近一小時數據
SELECT CONCAT(LEFT(now(),14) , '00:00') FROM DUAL;

-- 定期生成新表（影子表），然后修改新表为当前使用表
CREATE TABLE test_data_s.plan_t_new LIKE test_data_s.plan_t;
INSERT INTO test_data_s.plan_t SELECT * FROM test_data_s.plan_t_old;
RENAME TABLE test_data_s.plan_t TO test_data_s.plan_t_old, test_data_s.plan_t_new to test_data_s.plan_t;

SELECT DATE_FORMAT(pt.create_time,'%Y-%m') ym, COUNT(1) ct FROM test_data_s.plan_t pt 
GROUP BY DATE_FORMAT(pt.create_time,'%Y-%m') ORDER BY DATE_FORMAT(pt.create_time,'%Y-%m') DESC;

ALTER TABLE plan_t MODIFY COLUMN update_by VARCHAR(50) DEFAULT 'sys3' COMMENT '最后更新人';
ALTER TABLE plan_t ALTER COLUMN update_by SET DEFAULT 'system';

SELECT COUNT(1) FROM plan_t;

-- 删除存储过程
DROP PROCEDURE if EXISTS insert_plan_proc;
-- 定义存储过程
delimiter $$ -- 设置语句结束符
CREATE PROCEDURE insert_plan_proc(in insert_count int)
BEGIN
	WHILE insert_count > 0 DO
		INSERT INTO plan_t_old(title, start_time, end_time, create_time, update_time, id)
		VALUES('title', NOW(), NOW(), NOW(), NOW(), UUID()); -- 逐条自动提交
		SET insert_count = insert_count-1; -- 循环结束条件
	END WHILE;
	COMMIT;
END $$
delimiter ;
-- 关闭自动提交
SHOW VARIABLES LIKE 'autocommit';
SET autocommit='OFF'; -- 关闭会话级别的自动提交 
-- 调用存储过程
CALL insert_plan_proc(500000); -- 关闭自动提交后，每秒插入约一万次; 分多次提交(例如一万条数据提交一次)可以提高效率

SELECT COUNT(1) FROM test_data_s.plan_t pt;
FLUSH TABLES WITH READ LOCK; -- 关闭所有表，并加全局读锁

SELECT * FROM test_data_s.knowlege_tag;
SELECT * FROM test_data_s.knowlege_point;
INSERT INTO test_data_s.knowlege_tag VALUES(1,'mysql',NOW(),NOW());
INSERT INTO test_data_s.knowlege_point VALUES(UUID(), '共享锁和排他锁', '2', '1', '','1','','',NOW(),NOW());

SELECT count(1) FROM plan_t;
SELECT count(1) FROM plan_t_old;
TRUNCATE plan_t;

SHOW PROCESSLIST;

SELECT * FROM test_data_s.plan_t_old p WHERE p.id = '4028838372562191017256a28fe10004';

SELECT * FROM test_data_s.plan_t_old p WHERE p.title LIKE '保存';
SELECT * FROM test_data_s.plan_t_old p LIMIT 1000,1010;
UPDATE test_data_s.plan_t_old SET title='为title建立索引' WHERE id = '50cb59d3-b174-11ea-a3c5-00163e0b4814';

SELECT province.area_name province, city.area_name city 
FROM geo_t province 
INNER JOIN geo_t city ON province.area_code = city.parent_id
WHERE province.area_name LIKE '江苏省';

SELECT COUNT(1) FROM geo_t gt WHERE gt.area_type = '2';
SELECT * FROM geo_t gt WHERE gt.area_type = '4';
SELECT * FROM geo_t WHERE area_code LIKE '12%';

SELECT COUNT(1) FROM geo_t;

-- 20200705-------------------------------------------------------------------------------------------------------
SELECT gtn.area_name 省份, g.ct 数量
FROM geo_t gtn
INNER JOIN (
	SELECT COUNT(1) ct, SUBSTR(gt.area_code, 1, 2) at 
	FROM geo_t gt GROUP BY at
) g on SUBSTR(gtn.area_code, 1, 2) = g.at
WHERE gtn.area_type = '2'
ORDER BY g.ct desc;

CREATE TABLE geo_t_bk_1116 LIKE geo_t; -- 创建表结构
INSERT INTO geo_t_bk_1116 SELECT * FROM geo_t; -- 写数据
SELECT * FROM geo_t_bk_1116;
-- 42523
SELECT COUNT(1) FROM test_data_s.geo_t_bk_1116;
SELECT * FROM test_data_s.geo_t_bk_1116 gtb WHERE gtb.area_name like '%西单%';
SHOW PROFILES;
SHOW PROFILE cpu, block io FOR QUERY 213;
SELECT * FROM test_data_s.geo_t_bk_1116 gtb WHERE gtb.id = '297e05e1731af8cf01731afa917008ab';

SHOW PROFILE
SELECT * FROM test_data_s.geo_t_bk_1116 gtb WHERE gtb.area_name = '西单商场社区居委会';
SELECT * FROM test_data_s.geo_t_bk_1116;

INSERT INTO test_data_s.geo_t_bk_1116 
SELECT UUID() as id, gtb.create_by, gtb.create_time,
gtb.update_by, gtb.update_time, gtb.area_code, gtb.area_name, 
gtb.area_type, gtb.geo, gtb.parent_code FROM test_data_s.geo_t_bk_1116 gtb;

SELECT * FROM test_data_s.geo_t_bk_1116 ORDER BY RAND() LIMIT 1;

SELECT SUM(area_type = '2') FROM geo_t_bk_1116;
SELECT SUM(area_type) FROM geo_t_bk_1116;
SELECT COUNT(1) FROM geo_t_bk_1116;
SELECT * FROM geo_t_bk_1116 WHERE area_type = '2';

-- 数值越大，选择要越好(即越可能唯一)
SELECT 
COUNT(DISTINCT area_type)/ COUNT(1) area_type_selectivity,
COUNT(DISTINCT area_code)/ COUNT(1) area_code_selectivity,
COUNT(DISTINCT DATE_FORMAT(create_time,'%Y-%m-%d %H:%i'))/ COUNT(1) minute_selectivity,
COUNT(1) 
FROM geo_t;

SELECT 
COUNT(DISTINCT area_type) area_type_selectivity
FROM geo_t_bk_1116;  -- area_type去重，统计去重后数量，即area_type的种类数

SELECT 
COUNT(area_type) area_type_selectivity
FROM geo_t_bk_1116;

-- area_name 前缀索引位数5，后面增加位数，索引选择性增加不大
SELECT 
COUNT(DISTINCT LEFT(area_name, 1))/COUNT(1) sel1,
COUNT(DISTINCT LEFT(area_name, 2))/COUNT(1) sel2,
COUNT(DISTINCT LEFT(area_name, 3))/COUNT(1) sel3,
COUNT(DISTINCT LEFT(area_name, 4))/COUNT(1) sel4,
COUNT(DISTINCT LEFT(area_name, 5))/COUNT(1) sel5,
COUNT(DISTINCT LEFT(area_name, 6))/COUNT(1) sel6,
COUNT(DISTINCT LEFT(area_name, 7))/COUNT(1) sel7,
COUNT(DISTINCT LEFT(area_name, 8))/COUNT(1) sel8
FROM geo_t_bk_1116 gtb;
ALTER TABLE geo_t_bk_1116 ADD KEY (area_name(5)); -- 加前缀索引
DROP INDEX area_name ON geo_t_bk_1116; -- 删除索引
-- 删除前缀索引查询1.2s，加前缀索引查0.6s
SELECT * FROM test_data_s.geo_t_bk_1116 gtb WHERE gtb.area_name = '西单商场社区居委会';
SELECT * FROM test_data_s.geo_t_bk_1116 gtb WHERE gtb.area_name = '安徽省';
CREATE TABLE geo_t_bk_1116_i LIKE geo_t_bk_1116;
INSERT INTO geo_t_bk_1116_i SELECT * FROM geo_t_bk_1116;
ALTER TABLE geo_t_bk_1116_i ADD KEY area_name_index (area_name(5)); -- 加前缀索引
SELECT * FROM test_data_s.geo_t_bk_1116_i gtbi WHERE gtbi.area_name = '西单商场社区居委会';
SELECT * FROM test_data_s.geo_t_bk_1116_i gtbi WHERE gtbi.area_name = '安徽省';

SELECT gtbi.id FROM test_data_s.geo_t_bk_1116 gtbi WHERE gtbi.area_name = '上海市';


EXPLAIN
SELECT * FROM sys_user_t su
LEFT JOIN sys_user_role_t sur on su.id = sur.user_idLEFT JOIN sys_role_t sr on sur.role_id = sr.id
 
SHOW INDEX FROM	geo_t_bk_1116_i1;
SHOW INDEX FROM	geo_t_bk_1116_i;
SELECT * FROM geo_t_bk_1116_i1 g ORDER BY area_code LIMIT 0,10;
SHOW STATUS LIKE 'last_query_cost';

(SELECT * FROM test_data_s.geo_t_bk_1116 gtb
ORDER BY gtb.create_time
LIMIT 10)
UNION
(SELECT * FROM test_data_s.geo_t_bk_1116 gtb
ORDER BY gtb.create_time
LIMIT 10)
limit 10
































