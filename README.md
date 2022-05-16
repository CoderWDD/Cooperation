# Cooperation
A project that provices the api interface for fontend,which helps people to manage their daily project whenever cooperat with others or self.

# 需求分析：
  - 为什么要做：
    - 原因一：
        - 现状：在现有的To Do List软件中，都是以细化粒度来实现对时间的划分安排
        - 现实：我们在对学习/项目等进行安排计划时，很难将其粒度细化，此时，不妨将粒度划分地稍大些，将每日每时的计划，安排为阶段性来实施
        - 好处：划分简易，任务大体进度把握有度
    - 原因二：
        - 现状：在进行团队合作的时候，我们很需要将项目进行分工，并且可以实时了解到其他人的进度，方便调整自己的进度和需求
    - 原因三：
        - 现状：市面上的大部分类似软件，要么功能过于单一简单，要么功能种类繁多，上手有一定门槛，为学习增加了无形的压力
        - 好处：自定义性高，上手无难道，基本必须功能整合

# 数据表关系： 
![image](https://raw.githubusercontent.com/CoderWDD/myImages/main/blog_images/20220516135422.png)

# 数据表创建：
  - user表：
    ```
    create table user(
        user_id int unique not null auto_increment primary key comment '主键id，自增长',
        user_name varchar(64) not null default 'username' comment '用户名称',
        nick_name varchar(64) default 'nickname' comment '用户昵称',
        password varchar(64) not null default 'password' comment '用户密码，默认为NULL',
        phone varchar(32) default null comment '用户注册时候的手机号',
        first_name varchar(64) default 'firstname' comment '用户名字',
        last_name varchar(64) default 'lastname' comment '用户姓氏',
        department varchar(64) default 'department' comment '用户所属部门',
        `description` varchar(128) default 'description' comment '用户个性签名',
        avatar mediumblob default null comment '用户头像',
        delete_flag int default '0' comment '删除标志（0代表未删除，1代表已删除）',
        create_time datetime default now() comment '用户注册时间，默认为当前时间，类型为：YYYY-MM-DD HH:MM:SS',
        sex varchar(1) default '0' comment '用户性别（0男，1女，2未知）'
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 comment '用户表';
    ```
  - project表：
    ```
    create table project(
        project_iD int auto_increment unique not null primary key comment '项目id，自增长',
        `status` varchar(32) default 'todo' comment '项目当前状态，默认未完成',
        project_name varchar(64) default 'project name' comment '项目名，默认为project name',
        project_time datetime default now() comment '项目创建时间，默认为当前时间，类型为：YYYY-MM-DD HH:MM:SS',
        `description` varchar(100) default 'no description' comment '项目描述，简介',
        priority int default 1 comment '1：重要紧急；2：重要不紧急；3：不重要紧急；4：不重要不紧急',
        `invitation_code` varchar(32) unique default '0' comment '该项目的邀请码',
        author varchar(32) default 'author' comment '项目的创建者，默认为author'
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 comment '项目表';
    ```
  - taskitem表：
    ```
    create table taskitem(
        `item_id` int auto_increment unique not null primary key comment '任务id，自增长',
        project_id int not null default 0 comment '任务所属的项目的id，默认为0，即0为所有任务共属的项目id',
        project_name varchar(64) default 'project name' comment '项目名，默认为project name',
        `status` varchar(32) not null default '未完成' comment '任务当前状态，默认未完成',
        author varchar(64) default 'author' comment '任务的创建者，默认为author',
        executor varchar(64) default 'executor' comment '任务的执行者，默认为executor',
        priority int default 1 comment '1：重要紧急；2：重要不紧急；3：不重要紧急；4：不重要不紧急',
        item_name varchar(64) default 'item name' comment '任务名，默认为item name',
        item_time datetime default now() comment '任务创建时间，默认为当前时间，类型为：YYYY-MM-DD HH:MM:SS',
        `description` varchar(128) default 'description' comment '任务介绍、简介，默认为description'
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 comment '任务表';
    ```
  - uidpid表：
    ```
    create table uidpid(
        user_id int not null comment '用户id',
        project_id int not null default 0 comment '项目id，默认为0'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '用户项目关联表';
    ```
  - pidtid表：
    ```
    create table pidtid(
        project_id int not null default 0 comment '项目id，默认为0',
        item_id int not null comment '任务id'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '项目任务关联表';
    ```
  - uidpidauid表：
    ```
    create table uidpidauid(
        user_id int not null comment '用户id',
        project_id int not null comment'项目id',
        au_id int not null comment '权限id'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '用户权限管理表';
    ```
  - authentication表：
    ```
    create table authentication(
        au_id int auto_increment not null primary key comment '权限id，从1开始自增长',
        an_name varchar(32) not null default 'user' comment '权限名，默认为普通'
    )ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 comment '权限表';
    ```
  - uidtidauid表：
    ```
    create table uidtidauid(
        user_id int not null comment '用户id',
        item_id int not null comment '任务id',
        au_id int not null comment '权限id'
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '用户任务权限关联表';
    ```
