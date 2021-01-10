-- ----------------------------
-- 字典类型表
-- ----------------------------
drop table if exists tbl_dict_type;
create table sys_dict_type
(
  dict_id          int(11)      not null auto_increment    comment ''字典主键'',
  dict_name        varchar(100)    default ''''                 comment ''字典名称'',
  dict_type        varchar(100)    default ''''                 comment ''字典类型'',
  status            tinyint(4)        default 0      comment ''状态：0正常，1停用 -1删除'',
  remark            varchar(500)    default ''''                 comment ''备注'',
  create_user       varchar (50)           default null   comment ''创建者'',
  create_time 	    bigint(13)        default null        comment ''创建时间'',
  update_user       varchar (50)         default null       comment ''更新者'',
  update_time       bigint(13)        default null       comment ''更新时间'',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = ''字典类型表'';


-- ----------------------------
-- 字典数据表
-- ----------------------------
drop table if exists tbl_dict_data;
create table sys_dict_data
(
  dict_code        int(11)      not null auto_increment    comment ''字典编码'',
  dict_label       varchar(100)    default ''''                 comment ''字典标签'',
  dict_value       varchar(100)    default ''''                 comment ''字典键值'',
  dict_type        varchar(100)    default ''''                 comment ''字典类型'',
  sort        tinyint(4)          default 0                  comment ''字典排序'',
  is_default       char(1)         default ''N''                comment ''是否默认（Y是 N否）'',
  status            tinyint(4)        default 0                 comment ''状态：0正常，1停用 -1删除'',
  remark            varchar(500)    default ''''                 comment ''备注'',
  create_user       varchar (50)           default null   comment ''创建者'',
  create_time 	    bigint(13)        default null        comment ''创建时间'',
  update_user       varchar (50)         default null       comment ''更新者'',
  update_time       bigint(13)        default null       comment ''更新时间'',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = ''字典数据表'';

DROP TABLE IF EXISTS `tbl_resource`;
CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键ID',
  `name` varchar(100) NOT NULL COMMENT '文件名',
  `exts` varchar(10) NOT NULL COMMENT '扩展名',
  `md5` varchar(50) NOT NULL COMMENT '唯一标识',
  `path` varchar(200) NOT NULL COMMENT '保存路径',
  `upload_user_id` int(11) NOT NULL COMMENT '上传用户',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `size` int(11) DEFAULT NULL COMMENT '文件大小',
  `cover` varchar(200) DEFAULT NULL COMMENT '封面',
  `length` int(11) DEFAULT NULL COMMENT '长度',
  `pre_file` varchar(200) DEFAULT NULL COMMENT '预览文件',
  `status` char(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;