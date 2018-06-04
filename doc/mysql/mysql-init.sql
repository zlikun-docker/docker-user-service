-- ===============================================================================
-- MySQL 实例数据初始化脚本
-- MySQL官方镜像中提供了容器启动时自动执行 /docker-entrypoint-initdb.d 文件夹下的脚本的功能(包括shell脚本和sql脚本)
-- 因此只需要将本文件放在该目录下，即可自动初始化
-- ===============================================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS user DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE user;

-- 创建用户表
create table if not exists user (
  id bigint auto_increment primary key ,
  nickname varchar(64) not null comment '用户昵称(真实姓名)',
  mobile char(11) not null unique ,
  password char(32) not null comment '用户密码，使用MD5编码',
  ctime datetime not null comment '用户注册时间',
  status tinyint default 0 comment '用户状态：0正常状态、1锁定状态、-1注销状态'
) ;

-- 使用`on duplicate key update`避免重复写入测试数据
insert into user (id, nickname, mobile, password, ctime) values (1, 'zlikun', '12100000000', md5('123456'),now()) on duplicate key update nickname = 'zlikun';