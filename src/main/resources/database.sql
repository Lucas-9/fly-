drop database `lblog`;
create database `lblog` character set utf8mb4 collate utf8mb4_unicode_ci;
use `lblog`;
-- 关闭外键约束
set foreign_key_checks = 0;

-- ----------------------------
-- table structure for category
-- ----------------------------
drop table if exists `category`;
create table `category`
(
    `id`               bigint(32)                              not null auto_increment comment 'id',
    `name`             varchar(512) collate utf8mb4_unicode_ci not null comment '标题',
    `content`          text collate utf8mb4_unicode_ci comment '内容',
    `summary`          text collate utf8mb4_unicode_ci comment '概括',
    `icon`             varchar(128) collate utf8mb4_unicode_ci default null comment '图标',
    `article_count`       int(11) unsigned                        default '0' comment '该分类的文章数量',
    `order_num`        int(11)                                 default null comment '排序编码',
    `parent_id`        bigint(32) unsigned                     default null comment '父级分类的id',
    `meta_keywords`    varchar(256) collate utf8mb4_unicode_ci default null comment 'seo关键字',
    `meta_description` varchar(256) collate utf8mb4_unicode_ci default null comment 'seo描述内容',
    `created`          datetime                                default null comment '创建日期',
    `modified`         datetime                                default null comment '修改时间',
    `status`           tinyint(2)                              default null comment '状态',
    primary key (`id`)
) engine = innodb
  auto_increment = 5
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- ----------------------------
-- records of category
-- ----------------------------
insert into `category`
values ('1', '提问', null, null, null, '0', null, null, null, null, '2021-05-28 22:36:48', null, '1');
insert into `category`
values ('2', '分享', null, null, null, '0', null, null, null, null, '2021-05-28 22:36:48', null, '1');
insert into `category`
values ('3', '讨论', null, null, null, '0', null, null, null, null, '2021-05-28 22:36:48', null, '1');
insert into `category`
values ('4', '建议', null, null, null, '0', null, null, null, null, '2021-05-28 22:36:48', null, '0');

-- ----------------------------
-- table structure for comment
-- ----------------------------
drop table if exists `comment`;
create table `comment`
(
    `id`        bigint(32)                          not null auto_increment comment '主键id',
    `content`   longtext collate utf8mb4_unicode_ci not null comment '评论的内容',
    `parent_id` bigint(32)                                   default null comment '回复的评论id',
    `article_id`   bigint(32)                          not null comment '评论的文章id',
    `user_id`   bigint(32)                          not null comment '评论的用户id',
    `vote_up`   int(11) unsigned                    not null default '0' comment '“顶”的数量',
    `vote_down` int(11) unsigned                    not null default '0' comment '“踩”的数量',
    `level`     tinyint(2) unsigned                 not null default '0' comment '置顶等级',
    `status`    tinyint(2)                                   default null comment '评论的状态',
    `created`   datetime                            not null comment '评论的时间',
    `modified`  datetime                                     default null comment '评论的更新时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- ----------------------------
-- records of comment
-- ----------------------------
insert into `comment`
values ('1', '666',null, 1, 1, 0, 0, 0, null,'2021-05-28 14:41:41', '2021-05-28 14:41:41');
insert into `comment`
values ('2', '777',null, 2, 1, 0, 0, 0, null,'2021-05-28 14:41:41', '2021-05-28 14:41:41');
insert into `comment`
values ('3', '888',null, 3, 1, 0, 0, 0, null,'2021-05-28 14:41:41', '2021-05-28 14:41:41');


-- ----------------------------
-- table structure for article
-- ----------------------------
drop table if exists `article`;
create table `article`
(
    `id`            bigint(32)                              not null auto_increment comment 'id',
    `title`         varchar(128) collate utf8mb4_unicode_ci not null comment '标题',
    `content`       longtext collate utf8mb4_unicode_ci     not null comment '内容',
    `edit_mode`     varchar(32) collate utf8mb4_unicode_ci  not null default '0' comment '编辑模式：html可视化，markdown ..',
    `category_id`   bigint(32)                                       default null comment '分类id',
    `user_id`       bigint(32)                                       default null comment '用户id',
    `vote_up`       int(11) unsigned                        not null default '0' comment '支持人数',
    `vote_down`     int(11) unsigned                        not null default '0' comment '反对人数',
    `view_count`    int(11) unsigned                        not null default '0' comment '访问量',
    `comment_count` int(11)                                 not null default '0' comment '评论数量',
    `recommend`     tinyint(1)                                       default null comment '是否为精华',
    `level`         bigint(32)                              not null default '0' comment '置顶等级',
    `status`        tinyint(2)                                       default null comment '状态',
    `created`       datetime                                         default null comment '创建日期',
    `modified`      datetime                                         default null comment '最后更新日期',
    primary key (`id`)
) engine = innodb
  auto_increment = 4
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- ----------------------------
-- records of article
-- ----------------------------
insert into `article`
values ('1', 'title1','content1','0', '1', '1', '0', '0', '5', '0', '1', '1', null, '2021-05-28 14:41:41', '2021-05-28 14:41:41');
insert into `article`
values ('2', 'title2','content2','0', '1', '1', '0', '0', '3', '0', '1', '1', null, '2021-05-28 14:55:16', '2021-05-28 14:55:16');
insert into `article`
values ('3', 'title3', 'content3', '0', '1', '1', '0', '0', '1', '0', '0', '0', null,'2021-05-28 14:55:48', '2021-05-28 14:55:48');

-- ----------------------------
-- table structure for user
-- ----------------------------
drop table if exists `user`;
create table `user`
(
    `id`            bigint(32)                              not null auto_increment comment 'id',
    `username`      varchar(128) collate utf8mb4_unicode_ci not null comment '昵称',
    `password`      varchar(128) collate utf8mb4_unicode_ci not null comment '密码',
    `email`         varchar(64) collate utf8mb4_unicode_ci           default null comment '邮件',
    `mobile`        varchar(32) collate utf8mb4_unicode_ci           default null comment '手机电话',
    `point`         int(11) unsigned                        not null default '0' comment '积分',
    `sign`          varchar(255) collate utf8mb4_unicode_ci          default null comment '个性签名',
    `gender`        varchar(16) collate utf8mb4_unicode_ci           default null comment '性别',
    `wechat`        varchar(32) collate utf8mb4_unicode_ci           default null comment '微信号',
    `vip_level`     int(32)                                          default null comment 'vip等级',
    `birthday`      datetime                                         default null comment '生日',
    `avatar`        varchar(256) collate utf8mb4_unicode_ci not null comment '头像',
    `article_count`    int(11) unsigned                        not null default '0' comment '文章数量',
    `comment_count` int(11) unsigned                        not null default '0' comment '评论数量',
    `status`        tinyint(2)                              not null default '0' comment '状态',
    `lasted`        datetime                                         default null comment '最后的登陆时间',
    `created`       datetime                                not null comment '创建日期',
    `modified`      datetime                                         default null comment '最后修改时间',
    primary key (`id`),
    unique key `username` (`username`) using btree,
    unique key `email` (`email`) using btree
) engine = innodb
  auto_increment = 6
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- ----------------------------
-- records of user
-- 默认管理员账号：lucas，密码：123456
-- ----------------------------
insert into `user`values ('1', 'lucas', '96e79218965eb72c92a549dd5a330112', '11111@qq.com', null, '0', 'lucas','0', null, '0', null, '/url', '0', '0', '0', '2021-05-28 14:54:11','2021-05-28 14:37:24', null);

-- ----------------------------
-- table structure for role
-- ----------------------------
drop table if exists `role`;
create table `role`
(
    `id`            bigint(32)                              not null auto_increment comment 'id',
    `role_name`      varchar(128) collate utf8mb4_unicode_ci not null comment '角色名称',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- ----------------------------
-- records of role
-- ----------------------------
insert into `role`(`role_name`) values ('admin'),('user');

-- ----------------------------
-- table structure for user_role
-- ----------------------------
drop table if exists `user_role`;
create table `user_role`
(
    `id`      bigint(32) not null auto_increment,
    `user_id` bigint(32),
    `role_id` bigint(32),
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- ----------------------------
-- records of user_role
-- ----------------------------
insert into `user_role`(`user_id`, `role_id`) values (1, 1);

-- ----------------------------
-- table structure for user_action
-- ----------------------------
drop table if exists `user_action`;
create table `user_action`
(
    `id`         varchar(32) collate utf8mb4_unicode_ci not null default '',
    `user_id`    varchar(32) collate utf8mb4_unicode_ci          default null comment '用户id',
    `action`     varchar(32) collate utf8mb4_unicode_ci          default null comment '动作类型',
    `point`      int(11)                                         default null comment '得分',
    `article_id`    varchar(32) collate utf8mb4_unicode_ci          default null comment '关联的帖子id',
    `comment_id` varchar(32) collate utf8mb4_unicode_ci          default null comment '关联的评论id',
    `created`    datetime                                        default null comment '创建时间',
    `modified`   datetime                                        default null comment '修改时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;


-- ----------------------------
-- table structure for user_collection
-- ----------------------------
drop table if exists `user_favorites`;
create table `user_favorites`
(
    `id`           bigint(20) not null auto_increment comment '主键id',
    `user_id`      bigint(20) not null comment '用户id',
    `article_id`      bigint(20) not null,
    `article_user_id` bigint(20) not null,
    `created`      datetime   not null,
    `modified`     datetime   not null,
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- ----------------------------
-- table structure for user_message
-- ----------------------------
drop table if exists `user_message`;
create table `user_message`
(
    `id`           bigint(20) not null auto_increment,
    `user_id_of_send` bigint(20) not null comment '发送消息的用户id',
    `user_id_of_receive`   bigint(20) not null comment '接收消息的用户id',
    `article_id`      bigint(20) default null comment '消息可能关联的帖子',
    `comment_id`   bigint(20) default null comment '消息可能关联的评论',
    `content`      text collate utf8mb4_unicode_ci,
    `type`         tinyint(2) default null comment '消息类型',
    `created`      datetime   not null,
    `modified`     datetime   default null,
    `status`       int(11)    default null,
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- 表名 category,comment,article,user,user_action,user_collection,user_message,user_role,role

