-- 使用`on duplicate key update`避免重复写入测试数据
insert into user (id, nickname, mobile, password, ctime) values (1, 'zlikun', '12100000000', md5('123456'),now()) on duplicate key update nickname = 'zlikun';