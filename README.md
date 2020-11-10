# Design-for-Graduation-Java-Edition
使用Spring Boot的小型诊所信息系统，毕业设计Java版

#### 介绍
这是一个模拟小型诊所的项目，用于本人学习时编写的，基本功能完成

#### 软件架构
单机版的SpringBoot项目，（All in one）
使用集成SpringSecurity、Mybatis框架，使用thymeleaf模板引擎
数据库使用Mysql 同时使用 Redis 用于提高速度
#### 安装教程
1.  安装MySQL 、Redis (端口号默认)
2.  安装maven
2.  启用Idea
3.  导入项目
4.  在项目的resource目录下有myclinic.sql文件，将其导入到MySQL中
#### 已有账号信息
1. 系统管理员 用户名：user1 密码：123
2. 病人管理员 用户名：user3 密码：123
3· 血液科医生 用户名：user5 密码：5
4· 排队通知页 用户名：notice 密码：7
5· 药房管理员 用户名：pharmacy 密码：8
### 已完成功能
#### 系统管模块功能
1. 添加，修改员工信息
2. 添加，修改用户信息 ，维护用户登录权限
#### 病人管理模块功能
1. 病人信息的登记、修改
2. 病人挂号处理
2. 病人收费处理（收取处方、挂号费用，模拟功能）
#### 医生模块功能
1. 叫号处理
2. 病人病情记录
3. 病人诊断
4. 病史信息的查询
5. 给病人开出处方信息
6. 保存病人就诊记录（处方信息不保存）
7. 查询就诊结束的病人信息
8. 给病人开出支付信息
#### 药房模块功能
1. 药物信息的添加、修改
2. 对病人已支付的处方查询
3. 拿药结束后对药品库存的更新（事务管理使用Transaction注解）
### 待完善功能
1. 医生开出检查功能
2. 检查模块

