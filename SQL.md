---
title: '文件头信息'
---

> 知识点汇总

| 相关知识点 | 相关SQL          |
| ---------- | ---------------- |
| 存储过程   | PROCEDURE        |
| 修改表     | ALTER            |
| 安全更新   | sql_safe_updates |



## 1. ALTER TABLE

> 修改列**change** **modify** **alter**

- change 保留新的列 和旧的列
- modfiy 只保留旧的
- auto_increment是表的全局变量 只能对表修改

## 2. sql_safe_updates

> 安全模式 **update** 和 **delete**

- SHOW VARIABLES LIKE 'sql_safe_updates'
- SET sql_safe_updates = 1;
- 安全模式下update 和 delete 必须指定**主键**作为过滤条件 或者**limit**

## 3. 存储过程

> 通过存储过程执行复杂的sql，保证sql的一致性

- 存储过程中如果使用 **;** 会打断存储过程，需要通过**delimiter** 重新定义结束符
- **OUT** 为输出参数，输出参数在调用存储过程时保存为mysql中变量，然后通过select可使用变量
- **IN** 为输入参数，输入参数在存储过程中作为条件使用
- **INTO** 在存储过程中将查询到的结果保存为输出参数
- **DECLARE** 申明局部变量，局部变量在存储过程中临时使用，输出时赋值给**OUT** 变量即可

## 4. 异常处理

> 定义Controller层异常处理

- 业务异常返回状态码200 , 业务异常是希望传递给前端的错误信息

- 其他异常返回状态码500，其他异常(各种RuntimeException 和Exception)

  

## 5. 事务管理

> springboot中通过@transactional定义事务

1、事务隔离，不同的事务隔离级别会导致并发时不同的读取情况

uncommiet read,  commit read, repeatable read, serializable

**未提交读**，即一个事务可以读取到另一个事务没有提交的数据，如果提交事务前数据再次变化，最终保存数据库，即再次读取会读取到不同的数据，即脏读的情况；**--A事务不加锁，还能读取到没有提交事务的数据**

**已提交读，**A事务查询数据时，B事务允许修改数据，但是B只有提交后A才能读取到修改后的数据，会导致A事务在B提交前后读取到的数据不同，即不可重复读；**--相当于A事务不加锁**

**可重复读，**A事务查询数据时，B事务不允许update A事务的数据，则不会不可重复都得情况，但是B允许insert数据，即依然会出现幻读的情况；**-- 相当于A事务锁行**

**序列化**，A事务查询数据时，B不允许update ，不允许insert，不会出现幻读的情况，效率慢。**--相当于A事务锁表**

oracle默认已提交读，mysql默认可重复读

2、数据库锁



## 6.springboot国际化

> 1. LocaleResover 接口的四个实现类，通过bean注入覆盖spring默认的国际化方式
> 2. Swagger的配置类中开启设置Accept-Language的请求头参数(入参为zh-CN这种形式)
> 3. 工具类中通过contextHolder获取当前请求的语言环境，以及传入的key 从配置文件中读取值
> 4. 国际化配置文件的读取位置需要在application中配置,国际化文件名称按照指定的命名方式

  

## 7.springboot配置多个启动文件

> 1. 配置文件格式application-xxx.properties
>
> 2. 通过spring.profiles.active激活xxx配置文件
> 3. springboot的启动配置配置active.profiles为xxx，即可通过特定配置文件启动
> 4. applicaiton.properties为公用配置文件



## 8. spring事务的理解

> 事务隔离级别为default，事务传播特性为required

1. 增加事务注解的方法进入后开启事务，方法执行完毕事务提交

2. 事务没有提交前，数据没有实际插入数据库中，不能查询到数据

3. 方法中遇到异常，事务会回滚；如果异常被捕捉，方法执行完毕，则不会回滚

4. 如果父方法中增加事务注解，即开启事务，则子方法也会包含在事务中，父方法执行完毕提交事务

5. 方法中调用sql查询数据库，事务开启，mysql中查询事务的sql如下

   ```mysql
   SELECT * FROM information_schema.INNODB_trx;
   ```

6. spring事务管理通过**代理子类**调用方法，因此同一个类中方法调用**不会开启新的事务**，调用其他类中**注解的方法**会开启新的事物



## 9. 命令模式

> 将动作封装为命令对象，以便方便的传递和调用命令对象

1. 客户端、调用者、命令(命令接口，实际命令)、接收者
2. 调用者通过命令初始化
3. 命令中通过调用命令接口者的方法，具体执行命令



## 10、适配器模式

> 外观模式：组合多个子类，提供可以访问多个子类的简单方法
>
> 装饰模式：组合多个子类，在原来子类的基础上增加新的功能
>
> 适配器模式：按照新的接口框架去适配旧的接口，让客户通过新的方法实现旧的功能

1. 装饰模式：FileInputStream->BufferedInputStream->LIneNumberInputStream
2. 适配器模式：集合类实现的接口Emumeration（早期版本java）和Iterator（现在的java）



## 11、mysql8安装

> 初始化->安装mysql服务->启动mysql服务->连接mysql数据库->退出数据库连接

1. 解压zip包，准备my.ini配置文件放在解压目录中，**my.ini文件必须正确**

2. 管理员打开cmd，进入bin目录中

3. mysqld --initialize --console 初始化data目录(data目录根据ini配置文件指定目录生成，同时显示临时密码，**将临时密码复制到记事本中**，登录时粘贴密码，否则输入很容易错误导致不能登录

4. mysqld -install mysql8 安装指定名称的mysql服务，如果存在则跳过

5. **net start mysql8 如果启动失败，或者假启动，进入data中查看错误日志**

6. mysql -u root -p，粘贴临时密码登录

7. 重置密码，ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123';

8. 刷新全新，flush privileges; 退出，quit;

9. mysql -u root -p,提示输入密码，使用新密码重新连接mysql

   