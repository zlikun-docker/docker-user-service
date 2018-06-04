create table if not exists user (
  id bigint auto_increment primary key ,
  nickname varchar(64) not null comment '用户昵称(真实姓名)',
  mobile char(11) not null unique ,
  password char(32) not null comment '用户密码，使用MD5编码',
  ctime datetime not null comment '用户注册时间',
  status tinyint default 0 comment '用户状态：0正常状态、1锁定状态、-1注销状态'
) ;