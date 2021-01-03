-- ----------------------------
-- 字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          int(11)      not null auto_increment    comment '字典主键',
  dict_name        varchar(100)    default ''                 comment '字典名称',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  status            tinyint(4)        default 0      comment '状态：0正常，1停用 -1删除',
  remark            varchar(500)    default ''                 comment '备注',
  create_user       int(11)           default null   comment '创建者',
  create_time 	    bigint(13)        default null        comment '创建时间',
  update_user       int(11)           default null       comment '更新者',
  update_time       bigint(13)        default null       comment '更新时间',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = '字典类型表';


-- ----------------------------
-- 字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        int(11)      not null auto_increment    comment '字典编码',
  dict_label       varchar(100)    default ''                 comment '字典标签',
  dict_value       varchar(100)    default ''                 comment '字典键值',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  sort        tinyint(4)          default 0                  comment '字典排序',
  is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
  status            tinyint(4)        default 0                 comment '状态：0正常，1停用 -1删除',
  remark            varchar(500)    default ''                 comment '备注',
  create_user       int(11)           default null           comment '创建者',
  create_time 	    bigint(13)        default null        comment '创建时间',
  update_user       int(11)           default null       comment '更新者',
  update_time       bigint(13)        default null       comment '更新时间',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '字典数据表';



-- ----------------------------
-- 部门表
-- ----------------------------
drop table if exists sys_depart;
create table sys_depart (
  depart_id           int(11)           not null auto_increment    comment '部门id',
  parent_id         int(11)           default 0                  comment '父部门id',
  parent_ids        varchar(50)       default ''                 comment '祖级列表',
  name              varchar(50)       default ''                 comment '部门名称',
  sort              int(4)            default 0                  comment '显示顺序',
  manager           varchar(20)       default null               comment '负责人',
  mobile            varchar(11)       default null               comment '联系电话',
  email             varchar(50)       default null               comment '邮箱',
  status            tinyint(4)         default 0                  comment '部门状态：0正常，1停用 -1删除',
  remark            varchar(500)      default null               comment '备注',
  create_user       int(11)            default null               comment '创建者',
  create_time 	    bigint(13)         default null                comment '创建时间',
  update_user       int(11)            default null               comment '更新者',
  update_time       bigint(13)         default null                comment '更新时间',
  primary key (depart_id)
) engine=innodb auto_increment=1000 comment = '部门表';


-- ----------------------------
-- 用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           int(11)      not null auto_increment    comment '用户ID',
  depart_id         int(11)      default null               comment '部门ID',
  username          varchar(50)       not null                 comment '登录账号',
  password          varchar(50)     default ''                 comment '密码',
  nickname          varchar(50)     default ''                 comment '昵称',
  user_type         tinyint(4)      default 1                   comment '用户类型',
  email             varchar(50)     default ''                 comment '用户邮箱',
  mobile            varchar(11)     default ''                 comment '手机号码',
  sex               tinyint(4)      default 0                   comment '用户性别（0未知 1男 2女 ）',
  avatar            varchar(100)    default ''                 comment '头像路径',
  login_ip          varchar(50)     default ''                 comment '最后登录IP',
  login_date        bigint(13)           default null            comment '最后登录时间',
  pwd_update_date   bigint(13)        default null               comment '密码最后更新时间',
  status            tinyint(4)        default 0                 comment '帐号状态（0正常 1停用，-1删除）',
  remark            varchar(500)    default null               comment '备注',
  create_user       int(11)           default null             comment '创建者',
  create_time 	    bigint(13)         default null              comment '创建时间',
  update_user       int(11)           default null              comment '更新者',
  update_time       bigint(13)        default null              comment '更新时间',

  primary key (user_id)
) engine=innodb auto_increment=1000 comment = '用户信息表';

-- ----------------------------
-- 角色表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id           int(11)      not null auto_increment     comment '角色ID',
  role_name         varchar(20)     not null                   comment '角色名称',
  role_code         varchar(100)    not null                  comment '角色权限字符串',
  scope             tinyint(4)        default 1               comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  status            tinyint(4)         not null                   comment '角色状态状态（0正常 1停用，-1删除）',
  remark            varchar(500)    default null               comment '备注',
  create_user       int(11)           default null             comment '创建者',
  create_time 	    bigint(13)         default null              comment '创建时间',
  update_user       int(11)           default null              comment '更新者',
  update_time       bigint(13)        default null              comment '更新时间',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '角色信息表';


-- ----------------------------
-- 菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           int(11)      not null auto_increment    comment '菜单ID',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  menu_type         tinyint(4)       default null                 comment '菜单类型',
  menu_code         varchar(100)    default ''         comment '菜单权限字符串',
  sort              int(4)          default 0                  comment '显示顺序',
  url               varchar(200)    default '#'                comment '请求地址',
  target            varchar(20)     default ''                 comment '打开方式）',
  status            tinyint(4)        default 0                comment '菜单状态：0正常，1停用 -1删除',
  create_user       int(11)           default null           comment '创建者',
  create_time 	    bigint(13)        default null              comment '创建时间',
  update_user       int(11)           default null             comment '更新者',
  update_time       bigint(13)        default null            comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=1000 comment = '菜单权限表';


-- ----------------------------
-- 资源权限表
-- ----------------------------
drop table if exists sys_permission;
create table sys_permission (
  permission_id   int(11)      not null   auto_increment    comment '权限ID',
  permission_name          varchar(50)     not null                   comment '权限名称',
  permission_type          char(1)         default ''                 comment '权限类型',
  permission_code           char(1)         default 0                  comment '权限字符串',
  status            tinyint(4)        default 0      comment '状态：0正常，1停用 -1删除',
  remark            varchar(500)    default ''                 comment '备注',
  create_user       int(11)           default null   comment '创建者',
  create_time 	    bigint(13)        default null        comment '创建时间',
  update_user       int(11)           default null       comment '更新者',
  update_time       bigint(13)        default null       comment '更新时间',
  primary key (permission_id)
) engine=innodb auto_increment=1000 comment = '菜单权限表';


-- ----------------------------
-- 用户角色关联表
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  id       int(11)      not null   auto_increment    comment 'ID',
  user_id   int(11) not null comment '用户ID',
  role_id   int(11) not null comment '角色ID',
  primary key(id)
) engine=innodb comment = '用户角色关联表';

-- ----------------------------
-- 角色和菜单关联表
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  id        int(11)      not null   auto_increment    comment 'ID',
  role_id   int(11) not null comment '角色ID',
  menu_id   int(11) not null comment '菜单ID',
  primary key(id)
) engine=innodb comment = '角色和菜单关联表';


-- ----------------------------
-- 系统访问记录
-- ----------------------------
drop table if exists sys_login_log;
create table sys_login_log (
  login_id         int(20)     not null auto_increment   comment '访问ID',
  login_name     varchar(50)    default ''                comment '登录账号',
  ip_address     varchar(50)    default ''                comment '登录IP地址',
  login_location varchar(255)   default ''                comment '登录地点',
  browser        varchar(50)    default ''                comment '浏览器类型',
  os             varchar(50)    default ''                comment '操作系统',
  status         tinyint(4)  default 0           comment '登录状态（0成功 1失败）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     bigint(13)                                 comment '访问时间',
  primary key (login_id)
) engine=innodb auto_increment=100 comment = '系统访问记录';

-- ----------------------------
-- 操作日志记录
-- ----------------------------
drop table if exists sys_operate_log;
create table sys_operate_log (
  oper_id           int(11)      not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     int(2)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
  method            varchar(100)    default ''                 comment '方法名称',
  request_method    varchar(10)     default ''                 comment '请求方式',
  operator_type     int(1)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
  oper_name         varchar(50)     default ''                 comment '操作人员',
  dept_name         varchar(50)     default ''                 comment '部门名称',
  oper_url          varchar(255)    default ''                 comment '请求URL',
  oper_ip           varchar(50)     default ''                 comment '主机地址',
  oper_location     varchar(255)    default ''                 comment '操作地点',
  oper_param        varchar(2000)   default ''                 comment '请求参数',
  json_result       varchar(2000)   default ''                 comment '返回参数',
  status            int(1)          default 0                  comment '操作状态（0正常 1异常）',
  error_msg         varchar(2000)   default ''                 comment '错误消息',
  oper_time         datetime                                   comment '操作时间',
  primary key (oper_id)
) engine=innodb auto_increment=100 comment = '操作日志记录';


